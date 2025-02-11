package lru;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LRUCache<K, V> {

    private final int capacity;
    int ttl;
    private final Map<K, Node<K, V>> map;
    private final DoublyLinkedList<K, V> list;
    private final ScheduledExecutorService scheduler;

    public LRUCache(int capacity, int ttl) {
        this.capacity = capacity;
        this.ttl = ttl;
        this.map = new HashMap<>();
        list = new DoublyLinkedList<>();

        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::removeExpiredEntries, ttl, ttl, TimeUnit.MILLISECONDS);
    }

    private synchronized void removeExpiredEntries() {
        long now = System.currentTimeMillis();
        map.entrySet().removeIf(entry -> entry.getValue().expiryTime < now);
    }

    public synchronized void put(K key, V value) {

        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            node.value = value;
            node.expiryTime = ttl + System.currentTimeMillis();
            list.moveToHead(node);
        } else {
            if (map.size() == capacity) {
                // capacity is reached
                list.removeNodeFromTail();
                map.remove(key);
            }
            Node newNode = new Node<>(key, value, ttl + System.currentTimeMillis());
            list.addNodeToHead(newNode);
            map.put(key, newNode);
        }
    }

    public synchronized V get(K key) {
        if (!map.containsKey(key))
            return null;
        Node<K, V> node = map.get(key);
        if (node.expiryTime < System.currentTimeMillis()) {
            list.removeNode(node);
            return null;
        } else {
            list.moveToHead(node);
            return node.value;
        }

    }

    class Node<K, V> {

        K key;
        V value;
        Node<K, V> prev, next;
        long expiryTime;

        public Node(K key, V value, long expiryTime) {
            this.key = key;
            this.value = value;
            this.expiryTime = expiryTime;
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.expiryTime = -1;
        }
    }

    class DoublyLinkedList<K, V> {
        private final Node<K, V> head;
        private final Node<K, V> tail;

        DoublyLinkedList() {
            head = new Node<K, V>(null, null);
            tail = new Node<K, V>(null, null);
            head.next = tail;
            tail.prev = head;
        }

        void addNodeToHead(Node<K, V> node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        void moveToHead(Node<K, V> node) {
            removeNode(node);
            addNodeToHead(node);
        }

        Node<K, V> removeNodeFromTail() {
            Node prev = tail.prev;
            removeNode(prev);
            return prev;
        }

        void removeNode(Node<K, V> node) {
            Node prev = node.prev;
            Node next = node.next;

            prev.next = next;
            next.prev = prev;
        }
    }
}

