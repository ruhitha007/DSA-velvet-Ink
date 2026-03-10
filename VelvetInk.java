import java.util.*;

// ============================================================
// VELVET INK - Complete DSA Implementation
// Website: Art Gallery, Literature, Fashion, Poetry Platform
// ============================================================

public class VelvetInk {

    // ============================================================
    // DATA MODELS - matching exact website content
    // ============================================================

    static class Artwork {
        String id;
        String title;
        String artist;
        String category;
        String description;
        int    likes;

        Artwork(String id, String title, String artist,
                String category, String description, int likes) {
            this.id          = id;
            this.title       = title;
            this.artist      = artist;
            this.category    = category;
            this.description = description;
            this.likes       = likes;
        }
    }

    static class User {
        String id;
        String name;
        String email;
        String password;
        String role;

        User(String id, String name, String email,
             String password, String role) {
            this.id       = id;
            this.name     = name;
            this.email    = email;
            this.password = password;
            this.role     = role;
        }
    }

    static class Poem {
        String id;
        String title;
        String body;
        String author;
        String inspiredBy;
        int    likes;

        Poem(String id, String title, String body,
             String author, String inspiredBy, int likes) {
            this.id         = id;
            this.title      = title;
            this.body       = body;
            this.author     = author;
            this.inspiredBy = inspiredBy;
            this.likes      = likes;
        }
    }

    // ============================================================
    // SECTION 1 - UPLOAD ARTWORKS
    // Data Structure : Queue using Linked List
    // Matches        : upload.html - artworks queued for publishing
    // Algorithm      : FIFO - first uploaded = first processed
    // Enqueue O(1)     Dequeue O(1)
    // ============================================================

    static class QNode {
        Artwork data;
        QNode   next;
        QNode(Artwork data) { this.data = data; }
    }

    static class UploadQueue {
        QNode front, rear;
        int   size;

        void enqueue(Artwork a) {
            QNode node = new QNode(a);
            if (rear != null) rear.next = node;
            rear = node;
            if (front == null) front = rear;
            size++;
        }

        Artwork dequeue() {
            if (front == null) return null;
            Artwork a = front.data;
            front = front.next;
            if (front == null) rear = null;
            size--;
            return a;
        }

        boolean isEmpty() { return front == null; }
    }

    // ============================================================
    // SECTION 2 - DISPLAY ARTWORKS
    // Data Structure : Singly Linked List
    // Matches        : gallery.html - stores all artworks
    // Add O(1)   Display O(n)   Remove O(n)
    // ============================================================

    static class ArtNode {
        Artwork data;
        ArtNode next;
        ArtNode(Artwork data) { this.data = data; }
    }

    static class Gallery {
        ArtNode head;
        int     size;

        void addFront(Artwork a) {
            ArtNode node = new ArtNode(a);
            node.next = head;
            head = node;
            size++;
        }

        void display() {
            ArtNode cur = head;
            while (cur != null) {
                System.out.println("Title: "       + cur.data.title);
                System.out.println("Artist: "      + cur.data.artist);
                System.out.println("Category: "    + cur.data.category);
                System.out.println("Description: " + cur.data.description);
                System.out.println("Likes: "       + cur.data.likes);
                System.out.println();
                cur = cur.next;
            }
        }

        List<Artwork> toList() {
            List<Artwork> list = new ArrayList<>();
            ArtNode cur = head;
            while (cur != null) { list.add(cur.data); cur = cur.next; }
            return list;
        }
    }

    // ============================================================
    // SECTION 3 - DOUBLY LINKED LIST
    // Data Structure : Doubly Linked List
    // Matches        : fashion.html - editorial navigator
    //                  user can go next and previous between editorials
    // Next O(1)   Prev O(1)   Add O(1)
    // ============================================================

    static class DNode {
        Artwork data;
        DNode   prev, next;
        DNode(Artwork data) { this.data = data; }
    }

    static class EditorialNavigator {
        DNode head, tail, current;
        int   size;

        void add(Artwork a) {
            DNode node = new DNode(a);
            if (head == null) {
                head = tail = current = node;
            } else {
                node.prev = tail;
                tail.next = node;
                tail      = node;
            }
            size++;
        }

        Artwork next() {
            if (current != null && current.next != null) {
                current = current.next;
                return current.data;
            }
            return current != null ? current.data : null;
        }

        Artwork prev() {
            if (current != null && current.prev != null) {
                current = current.prev;
                return current.data;
            }
            return current != null ? current.data : null;
        }

        Artwork getCurrent() {
            return current != null ? current.data : null;
        }
    }

    // ============================================================
    // SECTION 4 - CIRCULAR LINKED LIST
    // Data Structure : Circular Linked List
    // Matches        : index.html gallery mosaic - rotating carousel
    //                  Crimson Solitude, Oceanic Dreams, Midnight Reverie, Metamorphosis
    // Rotate O(1)
    // ============================================================

    static class CNode {
        Artwork data;
        CNode   next;
        CNode(Artwork data) { this.data = data; }
    }

    static class FeaturedCarousel {
        CNode tail;
        int   size;

        void add(Artwork a) {
            CNode node = new CNode(a);
            if (tail == null) {
                tail = node;
                tail.next = tail;
            } else {
                node.next = tail.next;
                tail.next = node;
                tail      = node;
            }
            size++;
        }

        Artwork rotate() {
            if (tail == null) return null;
            tail = tail.next;
            return tail.data;
        }
    }

    // ============================================================
    // SECTION 5 - POLYNOMIAL ADT
    // Matches : gallery.html - image filter intensity curves
    //           f(x) = a0 + a1x + a2x^2 (colour filter math)
    // ============================================================

    static class PolyTerm {
        double coeff;
        int    exp;
        PolyTerm next;
        PolyTerm(double coeff, int exp) {
            this.coeff = coeff;
            this.exp   = exp;
        }
    }

    static class Polynomial {
        PolyTerm head;

        void addTerm(double coeff, int exp) {
            if (coeff == 0) return;
            PolyTerm node = new PolyTerm(coeff, exp);
            if (head == null || exp > head.exp) {
                node.next = head; head = node; return;
            }
            PolyTerm cur = head;
            while (cur.next != null && cur.next.exp > exp) cur = cur.next;
            if (cur.next != null && cur.next.exp == exp) {
                cur.next.coeff += coeff;
            } else {
                node.next = cur.next; cur.next = node;
            }
        }

        double evaluate(double x) {
            double result = 0;
            for (PolyTerm t = head; t != null; t = t.next)
                result += t.coeff * Math.pow(x, t.exp);
            return result;
        }

        static Polynomial add(Polynomial p1, Polynomial p2) {
            Polynomial result = new Polynomial();
            PolyTerm t1 = p1.head, t2 = p2.head;
            while (t1 != null && t2 != null) {
                if      (t1.exp > t2.exp) { result.addTerm(t1.coeff, t1.exp); t1 = t1.next; }
                else if (t2.exp > t1.exp) { result.addTerm(t2.coeff, t2.exp); t2 = t2.next; }
                else {
                    result.addTerm(t1.coeff + t2.coeff, t1.exp);
                    t1 = t1.next; t2 = t2.next;
                }
            }
            while (t1 != null) { result.addTerm(t1.coeff, t1.exp); t1 = t1.next; }
            while (t2 != null) { result.addTerm(t2.coeff, t2.exp); t2 = t2.next; }
            return result;
        }

        public String toString() {
            if (head == null) return "0";
            StringBuilder sb = new StringBuilder();
            for (PolyTerm t = head; t != null; t = t.next) {
                if (sb.length() > 0 && t.coeff > 0) sb.append(" + ");
                if      (t.exp == 0) sb.append(t.coeff);
                else if (t.exp == 1) sb.append(t.coeff + "x");
                else                 sb.append(t.coeff + "x^" + t.exp);
            }
            return sb.toString();
        }
    }

    // ============================================================
    // SECTION 6 - STACK
    // Data Structure : Stack using Linked List
    // Matches        : profile.html - bookmarks (LIFO)
    //                  last bookmarked = first shown
    // Push O(1)   Pop O(1)   Peek O(1)
    // ============================================================

    static class StackNode {
        Artwork   data;
        StackNode next;
        StackNode(Artwork data) { this.data = data; }
    }

    static class BookmarkStack {
        StackNode top;
        int       size;

        void push(Artwork a) {
            StackNode node = new StackNode(a);
            node.next = top;
            top = node;
            size++;
        }

        Artwork pop() {
            if (top == null) return null;
            Artwork a = top.data;
            top = top.next;
            size--;
            return a;
        }

        Artwork peek()    { return top != null ? top.data : null; }
        boolean isEmpty() { return top == null; }

        void display() {
            StackNode cur = top;
            while (cur != null) {
                System.out.println("Title: "       + cur.data.title);
                System.out.println("Artist: "      + cur.data.artist);
                System.out.println("Description: " + cur.data.description);
                System.out.println();
                cur = cur.next;
            }
        }
    }

    // ============================================================
    // SECTION 8 - CIRCULAR QUEUE
    // Data Structure : Circular Queue (array-based)
    // Matches        : community.html - notification feed
    //                  fixed buffer, old notifications drop when full
    // Enqueue O(1)   Dequeue O(1)
    // ============================================================

    static class CircularQueue {
        String[] arr;
        int front, rear, size, cap;

        CircularQueue(int cap) {
            this.cap = cap;
            arr = new String[cap];
            front = rear = size = 0;
        }

        void enqueue(String msg) {
            if (size == cap) {
                System.out.println("Queue full - removing oldest: " + arr[front]);
                front = (front + 1) % cap;
                size--;
            }
            arr[rear] = msg;
            rear = (rear + 1) % cap;
            size++;
        }

        String dequeue() {
            if (size == 0) return null;
            String msg = arr[front];
            arr[front] = null;
            front = (front + 1) % cap;
            size--;
            return msg;
        }

        void display() {
            int idx = front;
            for (int i = 0; i < size; i++) {
                System.out.println("Notification " + (i + 1) + ": " + arr[idx]);
                idx = (idx + 1) % cap;
            }
        }

        boolean isEmpty() { return size == 0; }
    }

    // ============================================================
    // SECTION 9 - DEQUE (Double Ended Queue)
    // Data Structure : Deque using LinkedList
    // Matches        : community.html - notifications
    //                  urgent alerts added to front
    //                  regular alerts added to back
    // All operations O(1)
    // ============================================================

    static class NotifDeque {
        LinkedList<String> deque = new LinkedList<>();

        void addUrgent(String msg)  { deque.addFirst(msg); }
        void addRegular(String msg) { deque.addLast(msg);  }

        String removeFirst() { return deque.isEmpty() ? null : deque.removeFirst(); }
        String removeLast()  { return deque.isEmpty() ? null : deque.removeLast();  }

        void display() {
            int i = 1;
            for (String msg : deque) {
                System.out.println("Notification " + i + ": " + msg);
                i++;
            }
        }

        boolean isEmpty() { return deque.isEmpty(); }
    }

    // ============================================================
    // SECTION 10 - MAX HEAP (Priority Queue)
    // Data Structure : Binary Max Heap
    // Matches        : gallery.html - trending artworks tab
    //                  artwork with most likes = highest priority
    // Insert O(log n)   Extract Max O(log n)   Peek O(1)
    // Parent of i = (i-1)/2
    // Left child  = 2i+1
    // Right child = 2i+2
    // ============================================================

    static class TrendingHeap {
        List<Artwork> heap = new ArrayList<>();

        void insert(Artwork a) {
            heap.add(a);
            siftUp(heap.size() - 1);
        }

        Artwork extractMax() {
            if (heap.isEmpty()) return null;
            Artwork max  = heap.get(0);
            Artwork last = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) { heap.set(0, last); siftDown(0); }
            return max;
        }

        Artwork peekMax() { return heap.isEmpty() ? null : heap.get(0); }

        void siftUp(int i) {
            while (i > 0) {
                int p = (i - 1) / 2;
                if (heap.get(p).likes < heap.get(i).likes) {
                    Collections.swap(heap, p, i);
                    i = p;
                } else break;
            }
        }

        void siftDown(int i) {
            int n = heap.size();
            while (true) {
                int l = 2*i+1, r = 2*i+2, big = i;
                if (l < n && heap.get(l).likes > heap.get(big).likes) big = l;
                if (r < n && heap.get(r).likes > heap.get(big).likes) big = r;
                if (big == i) break;
                Collections.swap(heap, i, big);
                i = big;
            }
        }

        void printTop(int k) {
            TrendingHeap copy = new TrendingHeap();
            copy.heap.addAll(this.heap);
            for (int i = 0; i < k && !copy.heap.isEmpty(); i++) {
                Artwork a = copy.extractMax();
                System.out.println("Rank " + (i + 1) + ":");
                System.out.println("Title: "       + a.title);
                System.out.println("Artist: "      + a.artist);
                System.out.println("Category: "    + a.category);
                System.out.println("Description: " + a.description);
                System.out.println("Likes: "       + a.likes);
                System.out.println();
            }
        }
    }

    // ============================================================
    // SECTION 11 - HASHING
    // Hash Function : polynomial rolling hash
    //                 h = sum of key[i] * 31^i mod tableSize
    // ============================================================

    static int hashFn(String key, int size) {
        long h = 0;
        for (char c : key.toCharArray()) h = (h * 31 + c) % size;
        return (int) h;
    }

    // Separate Chaining - User login table (login.html)
    // Each slot holds a linked list to handle collisions
    // Insert O(1) avg   Search O(1) avg
    static class UserLoginTable {
        static final int SIZE = 13;
        LinkedList<User>[] table;

        @SuppressWarnings("unchecked")
        UserLoginTable() {
            table = new LinkedList[SIZE];
            for (int i = 0; i < SIZE; i++) table[i] = new LinkedList<>();
        }

        void register(User u) {
            int idx = hashFn(u.email, SIZE);
            for (User existing : table[idx]) {
                if (existing.email.equals(u.email)) {
                    System.out.println("Email already registered: " + u.email);
                    return;
                }
            }
            table[idx].add(u);
        }

        User login(String email, String password) {
            int idx = hashFn(email, SIZE);
            for (User u : table[idx]) {
                if (u.email.equals(email) && u.password.equals(password))
                    return u;
            }
            return null;
        }

        void printTable() {
            for (int i = 0; i < SIZE; i++) {
                if (!table[i].isEmpty()) {
                    System.out.print("Slot [" + i + "]: ");
                    for (User u : table[i])
                        System.out.print(u.name + " (" + u.email + ") -> ");
                    System.out.println("null");
                }
            }
        }
    }

    // Open Addressing with Linear Probing - Artwork cache (gallery.html)
    // On collision: probe next available slot
    // Rehash when load factor > 0.7
    // Insert O(1) avg   Search O(1) avg
    static class ArtworkCache {
        static final int SIZE = 17;
        Artwork[] table   = new Artwork[SIZE];
        boolean[] deleted = new boolean[SIZE];
        int count = 0;

        void insert(Artwork a) {
            if ((double) count / SIZE >= 0.7) rehash();
            int idx = hashFn(a.id, SIZE);
            while (table[idx] != null && !deleted[idx])
                idx = (idx + 1) % SIZE;
            table[idx] = a; deleted[idx] = false; count++;
        }

        Artwork search(String id) {
            int idx = hashFn(id, SIZE), start = idx;
            while (table[idx] != null) {
                if (!deleted[idx] && table[idx].id.equals(id))
                    return table[idx];
                idx = (idx + 1) % SIZE;
                if (idx == start) break;
            }
            return null;
        }

        void delete(String id) {
            int idx = hashFn(id, SIZE);
            while (table[idx] != null) {
                if (!deleted[idx] && table[idx].id.equals(id)) {
                    deleted[idx] = true; count--; return;
                }
                idx = (idx + 1) % SIZE;
            }
        }

        void rehash() {
            Artwork[] old    = table.clone();
            boolean[] oldDel = deleted.clone();
            System.out.println("Rehashing triggered. Load factor exceeded 0.7.");
            count = 0; Arrays.fill(table, null); Arrays.fill(deleted, false);
            for (int i = 0; i < old.length; i++)
                if (old[i] != null && !oldDel[i]) insert(old[i]);
        }
    }

    // ============================================================
    // SEARCHING ALGORITHMS
    // Linear Search  O(n)     - search by title keyword
    // Binary Search  O(log n) - search by id (list must be sorted)
    // Running Time Analysis:
    //   Linear : T(n) = O(n) - checks every element
    //   Binary : T(n) = T(n/2) + O(1) => O(log n) by Master Theorem
    // ============================================================

    static Artwork linearSearch(List<Artwork> list, String keyword) {
        for (Artwork a : list) {
            if (a.title.toLowerCase().contains(keyword.toLowerCase()))
                return a;
        }
        return null;
    }

    static Artwork binarySearch(List<Artwork> sorted, String targetId) {
        int lo = 0, hi = sorted.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = sorted.get(mid).id.compareTo(targetId);
            if      (cmp == 0) return sorted.get(mid);
            else if (cmp < 0)  lo = mid + 1;
            else               hi = mid - 1;
        }
        return null;
    }

    // ============================================================
    // SORTING ALGORITHMS
    // Bubble Sort    O(n^2)       - by likes ascending
    // Insertion Sort O(n^2)       - by title A to Z
    // Selection Sort O(n^2)       - by artist A to Z
    // Merge Sort     O(n log n)   - by category
    // Quick Sort     O(n log n)   - by likes descending (Trending)
    //
    // Running Time Table:
    // Algorithm      Best        Average     Worst       Space
    // Bubble         O(n)        O(n^2)      O(n^2)      O(1)
    // Insertion      O(n)        O(n^2)      O(n^2)      O(1)
    // Selection      O(n^2)      O(n^2)      O(n^2)      O(1)
    // Merge          O(n log n)  O(n log n)  O(n log n)  O(n)
    // Quick          O(n log n)  O(n log n)  O(n^2)      O(log n)
    // ============================================================

    static void bubbleSort(List<Artwork> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).likes > list.get(j + 1).likes) {
                    Collections.swap(list, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    static void insertionSort(List<Artwork> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            Artwork key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).title
                                 .compareToIgnoreCase(key.title) > 0) {
                list.set(j + 1, list.get(j)); j--;
            }
            list.set(j + 1, key);
        }
    }

    static void selectionSort(List<Artwork> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++)
                if (list.get(j).artist
                        .compareToIgnoreCase(
                         list.get(minIdx).artist) < 0) minIdx = j;
            Collections.swap(list, i, minIdx);
        }
    }

    static void mergeSort(List<Artwork> list, int l, int r) {
        if (l >= r) return;
        int m = l + (r - l) / 2;
        mergeSort(list, l, m);
        mergeSort(list, m + 1, r);
        List<Artwork> L = new ArrayList<>(list.subList(l, m + 1));
        List<Artwork> R = new ArrayList<>(list.subList(m + 1, r + 1));
        int i = 0, j = 0, k = l;
        while (i < L.size() && j < R.size()) {
            if (L.get(i).category.compareToIgnoreCase(
                R.get(j).category) <= 0) list.set(k++, L.get(i++));
            else                         list.set(k++, R.get(j++));
        }
        while (i < L.size()) list.set(k++, L.get(i++));
        while (j < R.size()) list.set(k++, R.get(j++));
    }

    static void quickSort(List<Artwork> list, int low, int high) {
        if (low < high) {
            int pivot = list.get(high).likes;
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (list.get(j).likes >= pivot) {
                    i++; Collections.swap(list, i, j);
                }
            }
            Collections.swap(list, i + 1, high);
            int pi = i + 1;
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    // ============================================================
    // HELPER - print single artwork clean
    // ============================================================

    static void printArtwork(Artwork a) {
        System.out.println("Title: "       + a.title);
        System.out.println("Artist: "      + a.artist);
        System.out.println("Category: "    + a.category);
        System.out.println("Description: " + a.description);
        System.out.println("Likes: "       + a.likes);
    }

    // ============================================================
    // MAIN
    // ============================================================

    public static void main(String[] args) {

        System.out.println("====================================================");
        System.out.println("   VELVET INK - Art, Literature, Fashion, Poetry");
        System.out.println("====================================================");

        // ---- Artworks from gallery.html seed data ----
        Artwork a1 = new Artwork("s1", "Crimson Solitude",
            "Aria Blackwood",  "Painting",
            "This painting emerged from three sleepless nights after a difficult farewell.", 210);
        Artwork a2 = new Artwork("s2", "Oceanic Memory",
            "Kai Velmira",     "Digital Art",
            "I painted the sea as I remember my grandmother's voice.", 145);
        Artwork a3 = new Artwork("s3", "Midnight Reverie",
            "Selene Marsh",    "Photography",
            "Shot at 3am on a night when insomnia gave me a gift.", 103);
        Artwork a4 = new Artwork("s4", "Metamorphosis",
            "Rio Santos",      "Mixed Media",
            "Torn magazine pages, watercolor, and ash from burned letters.", 89);
        Artwork a5 = new Artwork("s5", "The Burning Hour",
            "Mira Cassell",    "Painting",
            "Every day has an hour where everything feels on the edge of transformation.", 67);
        Artwork a6 = new Artwork("s6", "Blossom Protocol",
            "Nadia Osei",      "Digital Art",
            "A commentary on how we present ourselves online vs who we truly are.", 55);
        Artwork a7 = new Artwork("s7", "Storm Language",
            "Eli Nakamura",    "Photography",
            "I spent six hours in the rain to capture this.", 42);
        Artwork a8 = new Artwork("s8", "Root Memory",
            "Soraya West",     "Mixed Media",
            "My grandmother's hands pressed into clay, surrounded by garden herbs.", 31);

        // --------------------------------------------------
        // FEATURE 1 - UPLOADING ARTWORKS
        // Data Structure : Queue using Linked List
        // --------------------------------------------------
        System.out.println();
        System.out.println("Uploading Artworks:");
        System.out.println();

        UploadQueue uploadQueue = new UploadQueue();
        uploadQueue.enqueue(a1); System.out.println("Queued: " + a1.title);
        uploadQueue.enqueue(a2); System.out.println("Queued: " + a2.title);
        uploadQueue.enqueue(a3); System.out.println("Queued: " + a3.title);
        uploadQueue.enqueue(a4); System.out.println("Queued: " + a4.title);
        uploadQueue.enqueue(a5); System.out.println("Queued: " + a5.title);
        uploadQueue.enqueue(a6); System.out.println("Queued: " + a6.title);
        uploadQueue.enqueue(a7); System.out.println("Queued: " + a7.title);
        uploadQueue.enqueue(a8); System.out.println("Queued: " + a8.title);

        System.out.println();
        Gallery gallery = new Gallery();
        while (!uploadQueue.isEmpty()) {
            Artwork uploaded = uploadQueue.dequeue();
            gallery.addFront(uploaded);
            System.out.println("Uploaded: " + uploaded.title);
        }
        System.out.println();
        System.out.println("All artworks uploaded successfully.");
        System.out.println("Total artworks in gallery: " + gallery.size);

        // --------------------------------------------------
        // FEATURE 2 - DISPLAYING ARTWORKS
        // Data Structure : Singly Linked List
        // --------------------------------------------------
        System.out.println();
        System.out.println("----------------------------------------------------");
        System.out.println("All Artworks:");
        System.out.println("----------------------------------------------------");
        System.out.println();
        gallery.display();

        // --------------------------------------------------
        // FEATURE 3 - DOUBLY LINKED LIST
        // Fashion Editorial Navigator
        // --------------------------------------------------
        System.out.println("----------------------------------------------------");
        System.out.println("Fashion Editorial Navigator (Doubly Linked List):");
        System.out.println("----------------------------------------------------");
        System.out.println();

        EditorialNavigator editorials = new EditorialNavigator();
        editorials.add(a1);
        editorials.add(a3);
        editorials.add(a5);
        editorials.add(a7);

        System.out.println("Current Editorial:");
        System.out.println("Title: "  + editorials.getCurrent().title);
        System.out.println("Artist: " + editorials.getCurrent().artist);
        System.out.println();
        System.out.println("Moving to Next Editorial:");
        Artwork nxt = editorials.next();
        System.out.println("Title: "  + nxt.title);
        System.out.println("Artist: " + nxt.artist);
        System.out.println();
        System.out.println("Moving to Next Editorial:");
        Artwork nxt2 = editorials.next();
        System.out.println("Title: "  + nxt2.title);
        System.out.println("Artist: " + nxt2.artist);
        System.out.println();
        System.out.println("Moving Back to Previous Editorial:");
        Artwork prv = editorials.prev();
        System.out.println("Title: "  + prv.title);
        System.out.println("Artist: " + prv.artist);
        System.out.println();

        // --------------------------------------------------
        // FEATURE 4 - CIRCULAR LINKED LIST
        // Homepage Featured Carousel
        // --------------------------------------------------
        System.out.println("----------------------------------------------------");
        System.out.println("Featured Carousel (Circular Linked List):");
        System.out.println("----------------------------------------------------");
        System.out.println();

        FeaturedCarousel carousel = new FeaturedCarousel();
        carousel.add(a1);
        carousel.add(a2);
        carousel.add(a3);
        carousel.add(a4);

        System.out.println("Rotating carousel (4 rotations):");
        for (int i = 0; i < 4; i++) {
            Artwork showing = carousel.rotate();
            System.out.println("Now Showing: " + showing.title + " by " + showing.artist);
        }
        System.out.println();

        // --------------------------------------------------
        // FEATURE 5 - POLYNOMIAL ADT
        // Image Filter Curves in Gallery
        // --------------------------------------------------
        System.out.println("----------------------------------------------------");
        System.out.println("Polynomial ADT - Gallery Image Filter Curves:");
        System.out.println("----------------------------------------------------");
        System.out.println();

        Polynomial brightness = new Polynomial();
        brightness.addTerm(2, 2); brightness.addTerm(3, 1); brightness.addTerm(1, 0);

        Polynomial contrast = new Polynomial();
        contrast.addTerm(1, 3); contrast.addTerm(-1, 1); contrast.addTerm(5, 0);

        System.out.println("Brightness Filter : p(x) = " + brightness);
        System.out.println("Contrast Filter   : p(x) = " + contrast);
        System.out.println("Combined Filter   : p(x) = " + Polynomial.add(brightness, contrast));
        System.out.println("Brightness at x=2 : " + brightness.evaluate(2));
        System.out.println("Contrast at x=2   : " + contrast.evaluate(2));
        System.out.println();

        // --------------------------------------------------
        // FEATURE 6 - SEARCHING
        // gallery.html search bar
        // --------------------------------------------------
        System.out.println("----------------------------------------------------");
        System.out.println("Searching Artwork:");
        System.out.println("----------------------------------------------------");
        System.out.println();

        List<Artwork> artList = gallery.toList();

        System.out.println("Linear Search - keyword: midnight");
        System.out.println("Time Complexity: O(n)");
        Artwork s1 = linearSearch(artList, "midnight");
        if (s1 != null) {
            System.out.println("Artwork Found:");
            System.out.println("Title: "       + s1.title);
            System.out.println("Artist: "      + s1.artist);
            System.out.println("Description: " + s1.description);
        } else {
            System.out.println("Artwork not found.");
        }
        System.out.println();

        System.out.println("Linear Search - keyword: crimson");
        Artwork s2 = linearSearch(artList, "crimson");
        if (s2 != null) {
            System.out.println("Artwork Found:");
            System.out.println("Title: "       + s2.title);
            System.out.println("Artist: "      + s2.artist);
            System.out.println("Description: " + s2.description);
        } else {
            System.out.println("Artwork not found.");
        }
        System.out.println();

        System.out.println("Linear Search - keyword: ocean");
        Artwork s3 = linearSearch(artList, "ocean");
        if (s3 != null) {
            System.out.println("Artwork Found:");
            System.out.println("Title: "       + s3.title);
            System.out.println("Artist: "      + s3.artist);
            System.out.println("Description: " + s3.description);
        } else {
            System.out.println("Artwork not found.");
        }
        System.out.println();

        System.out.println("Linear Search - keyword: unknown");
        Artwork s4 = linearSearch(artList, "unknown");
        System.out.println(s4 != null ? "Artwork Found: " + s4.title : "Artwork not found.");
        System.out.println();

        List<Artwork> sortedById = new ArrayList<>(artList);
        sortedById.sort(Comparator.comparing(a -> a.id));

        System.out.println("Binary Search - searching ID: s3");
        System.out.println("Time Complexity: O(log n)");
        Artwork b1 = binarySearch(sortedById, "s3");
        if (b1 != null) {
            System.out.println("Artwork Found:");
            System.out.println("Title: "  + b1.title);
            System.out.println("Artist: " + b1.artist);
        } else {
            System.out.println("Artwork not found.");
        }
        System.out.println();

        System.out.println("Binary Search - searching ID: s99");
        Artwork b2 = binarySearch(sortedById, "s99");
        System.out.println(b2 != null ? "Artwork Found: " + b2.title : "Artwork not found.");
        System.out.println();

        // --------------------------------------------------
        // FEATURE 7 - SORTING
        // gallery.html - sort artworks by different fields
        // --------------------------------------------------
        System.out.println("----------------------------------------------------");
        System.out.println("Sorting Artworks:");
        System.out.println("----------------------------------------------------");
        List<Artwork> sc;

        System.out.println();
        System.out.println("Bubble Sort - Likes Ascending (O(n^2)):");
        System.out.println();
        sc = new ArrayList<>(artList);
        bubbleSort(sc);
        for (Artwork a : sc) {
            System.out.println("Title: "  + a.title);
            System.out.println("Artist: " + a.artist);
            System.out.println("Likes: "  + a.likes);
            System.out.println();
        }

        System.out.println("Insertion Sort - Title A to Z (O(n^2)):");
        System.out.println();
        sc = new ArrayList<>(artList);
        insertionSort(sc);
        for (Artwork a : sc) {
            System.out.println("Title: "  + a.title);
            System.out.println("Artist: " + a.artist);
            System.out.println();
        }

        System.out.println("Selection Sort - Artist A to Z (O(n^2)):");
        System.out.println();
        sc = new ArrayList<>(artList);
        selectionSort(sc);
        for (Artwork a : sc) {
            System.out.println("Artist: " + a.artist);
            System.out.println("Title: "  + a.title);
            System.out.println();
        }

        System.out.println("Merge Sort - Category A to Z (O(n log n)):");
        System.out.println();
        sc = new ArrayList<>(artList);
        mergeSort(sc, 0, sc.size() - 1);
        for (Artwork a : sc) {
            System.out.println("Category: " + a.category);
            System.out.println("Title: "    + a.title);
            System.out.println("Artist: "   + a.artist);
            System.out.println();
        }

        System.out.println("Quick Sort - Likes Descending (Trending) (O(n log n)):");
        System.out.println();
        sc = new ArrayList<>(artList);
        quickSort(sc, 0, sc.size() - 1);
        for (Artwork a : sc) {
            System.out.println("Title: "  + a.title);
            System.out.println("Artist: " + a.artist);
            System.out.println("Likes: "  + a.likes);
            System.out.println();
        }

        // --------------------------------------------------
        // FEATURE 8 - BOOKMARKS
        // Data Structure : Stack (LIFO)
        // Matches profile.html bookmarks tab
        // --------------------------------------------------
        System.out.println("----------------------------------------------------");
        System.out.println("Showing Bookmarks:");
        System.out.println("----------------------------------------------------");
        System.out.println();

        BookmarkStack bookmarks = new BookmarkStack();

        System.out.println("User is bookmarking artworks...");
        System.out.println();
        bookmarks.push(a8); System.out.println("Bookmarked: " + a8.title);
        bookmarks.push(a6); System.out.println("Bookmarked: " + a6.title);
        bookmarks.push(a3); System.out.println("Bookmarked: " + a3.title);
        bookmarks.push(a1); System.out.println("Bookmarked: " + a1.title);

        System.out.println();
        System.out.println("Your Bookmarks (most recent first):");
        System.out.println();
        bookmarks.display();

        System.out.println("Top Bookmark (peek):");
        System.out.println("Title: "  + bookmarks.peek().title);
        System.out.println("Artist: " + bookmarks.peek().artist);
        System.out.println();

        System.out.println("Removing top bookmark...");
        Artwork removed = bookmarks.pop();
        System.out.println("Removed: " + removed.title);
        System.out.println();

        System.out.println("Bookmarks after removal:");
        System.out.println();
        bookmarks.display();

        // --------------------------------------------------
        // FEATURE 10 - NOTIFICATIONS
        // Circular Queue + Deque
        // Matches community.html notification feed
        // --------------------------------------------------
        System.out.println("----------------------------------------------------");
        System.out.println("Displaying Notifications (Circular Queue):");
        System.out.println("----------------------------------------------------");
        System.out.println();

        CircularQueue cq = new CircularQueue(5);
        cq.enqueue("Aria Blackwood liked your artwork.");
        cq.enqueue("Kai Velmira started following you.");
        cq.enqueue("Your poem received 10 new likes.");
        cq.enqueue("Selene Marsh commented on Midnight Reverie.");
        cq.enqueue("Rio Santos bookmarked your artwork.");

        System.out.println("Current Notifications:");
        System.out.println();
        cq.display();
        System.out.println();

        System.out.println("Adding new notification (queue is full):");
        cq.enqueue("Mira Cassell wants to collaborate with you.");
        System.out.println();
        System.out.println("Notifications after overflow:");
        System.out.println();
        cq.display();
        System.out.println();

        System.out.println("Reading all notifications one by one:");
        System.out.println();
        while (!cq.isEmpty()) System.out.println("Read: " + cq.dequeue());

        System.out.println();
        System.out.println("----------------------------------------------------");
        System.out.println("Displaying Notifications (Deque):");
        System.out.println("----------------------------------------------------");
        System.out.println();

        NotifDeque dq = new NotifDeque();
        dq.addRegular("Nadia Osei liked your poem.");
        dq.addRegular("Soraya West is now following you.");
        dq.addUrgent("URGENT: Your upload failed. Please retry.");
        dq.addRegular("Eli Nakamura shared your artwork.");
        dq.addUrgent("URGENT: New comment needs your attention.");

        System.out.println("All Notifications (urgent first):");
        System.out.println();
        dq.display();
        System.out.println();
        System.out.println("Removing urgent from front: " + dq.removeFirst());
        System.out.println("Removing oldest from back : " + dq.removeLast());
        System.out.println();
        System.out.println("Remaining Notifications:");
        System.out.println();
        dq.display();
        System.out.println();

        // --------------------------------------------------
        // FEATURE 11 - HASHING
        // Separate Chaining - User Login (login.html)
        // Open Addressing   - Artwork Cache (gallery.html)
        // --------------------------------------------------
        System.out.println("----------------------------------------------------");
        System.out.println("User Login System (Hashing - Separate Chaining):");
        System.out.println("----------------------------------------------------");
        System.out.println();

        UserLoginTable loginTable = new UserLoginTable();

        User u1 = new User("u1", "Aria Blackwood",  "aria@velvet.ink",    "pass123", "Visual Artist");
        User u2 = new User("u2", "Kai Velmira",     "kai@velvet.ink",     "pass456", "Writer / Poet");
        User u3 = new User("u3", "Celeste Fontaine","celeste@velvet.ink", "pass789", "Writer / Poet");
        User u4 = new User("u4", "Selene Marsh",    "selene@velvet.ink",  "pass321", "Visual Artist");
        User u5 = new User("u5", "Mira Cassell",    "mira@velvet.ink",    "pass654", "Fashion Creator");

        System.out.println("Registering users from register.html:");
        System.out.println();
        loginTable.register(u1); System.out.println("Registered: " + u1.name + " (" + u1.role + ")");
        loginTable.register(u2); System.out.println("Registered: " + u2.name + " (" + u2.role + ")");
        loginTable.register(u3); System.out.println("Registered: " + u3.name + " (" + u3.role + ")");
        loginTable.register(u4); System.out.println("Registered: " + u4.name + " (" + u4.role + ")");
        loginTable.register(u5); System.out.println("Registered: " + u5.name + " (" + u5.role + ")");

        System.out.println();
        System.out.println("Trying to register duplicate:");
        loginTable.register(u1);

        System.out.println();
        System.out.println("Hash Table Contents:");
        System.out.println();
        loginTable.printTable();

        System.out.println();
        System.out.println("Login Tests (from login.html):");
        System.out.println();
        User loggedIn1 = loginTable.login("aria@velvet.ink", "pass123");
        System.out.println("aria@velvet.ink + correct password -> " +
            (loggedIn1 != null ? "Login Successful. Welcome " + loggedIn1.name : "Login Failed"));

        User loggedIn2 = loginTable.login("aria@velvet.ink", "wrongpass");
        System.out.println("aria@velvet.ink + wrong password   -> " +
            (loggedIn2 != null ? "Login Successful." : "Login Failed"));

        User loggedIn3 = loginTable.login("unknown@velvet.ink", "pass123");
        System.out.println("unknown@velvet.ink                 -> " +
            (loggedIn3 != null ? "Login Successful." : "Login Failed"));

        System.out.println();
        System.out.println("----------------------------------------------------");
        System.out.println("Artwork Cache (Hashing - Open Addressing):");
        System.out.println("----------------------------------------------------");
        System.out.println();

        ArtworkCache cache = new ArtworkCache();
        System.out.println("Caching all artworks by ID:");
        System.out.println();
        for (Artwork a : artList) {
            cache.insert(a);
            System.out.println("Cached: " + a.id + " -> " + a.title);
        }

        System.out.println();
        System.out.println("Fast lookup by ID:");
        System.out.println();

        Artwork found1 = cache.search("s3");
        if (found1 != null) {
            System.out.println("Search s3:");
            System.out.println("Title: "  + found1.title);
            System.out.println("Artist: " + found1.artist);
        }
        System.out.println();

        Artwork found2 = cache.search("s7");
        if (found2 != null) {
            System.out.println("Search s7:");
            System.out.println("Title: "  + found2.title);
            System.out.println("Artist: " + found2.artist);
        }
        System.out.println();

        System.out.println("Search s99 (not in cache):");
        Artwork found3 = cache.search("s99");
        System.out.println(found3 != null ? "Found: " + found3.title : "Artwork not found.");
        System.out.println();

        System.out.println("Deleting s3 from cache (lazy delete)...");
        cache.delete("s3");
        Artwork afterDelete = cache.search("s3");
        System.out.println("Search s3 after delete: " +
            (afterDelete != null ? "Found." : "Artwork not found."));
        System.out.println();

        System.out.println("Extendible Hashing - Used for large distributed artwork storage:");
        System.out.println("Global depth = bits used to index the directory.");
        System.out.println("Local depth  = bits used within each bucket.");
        System.out.println("On overflow  = split bucket, double directory if needed.");
        System.out.println("Advantage    = only the affected bucket is reorganized.");
        System.out.println();

        // --------------------------------------------------
        // FEATURE 12 - TRENDING (MAX HEAP)
        // Matches gallery.html trending tab
        // --------------------------------------------------
        System.out.println("----------------------------------------------------");
        System.out.println("Trending Artworks (Max Heap - Priority Queue):");
        System.out.println("----------------------------------------------------");
        System.out.println();

        TrendingHeap trendingHeap = new TrendingHeap();
        for (Artwork a : artList) trendingHeap.insert(a);

        System.out.println("Top 5 Trending Artworks:");
        System.out.println();
        trendingHeap.printTop(5);

        System.out.println("Extracting number 1 trending artwork...");
        System.out.println();
        Artwork topArt = trendingHeap.extractMax();
        System.out.println("Extracted:");
        printArtwork(topArt);
        System.out.println();

        System.out.println("New Number 1 Trending:");
        System.out.println();
        Artwork newTop = trendingHeap.peekMax();
        if (newTop != null) printArtwork(newTop);
        System.out.println();

    }
}
