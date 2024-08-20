import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {


        List<Thread> threads = new ArrayList<>();
        String[] mas = new String[1000];

        for (int i = 0; i < mas.length; i++) {
            mas[i] = generateRoute("RLRFR", 100);
        }

        for (String masNew : mas) {
            String route = masNew;
            Runnable logic = () -> {
                Integer countR = 0;
                countR = quantity(route, 'R');
                System.out.println(route.substring(0, 100) + " -> " + countR);

                synchronized (sizeToFreq) {
                    sizeToFreq.put(countR, sizeToFreq.getOrDefault(countR, 0) + 1);
                }
            };
            Thread thread = new Thread(logic);
            threads.add(thread);
            thread.start();
        }
        // Ожидание завершения всех потоков
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int maxSize = 0;
        Integer maxFgeg = 0;
        for (Integer key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > maxSize) {
                maxSize = sizeToFreq.get(key);
                maxFgeg = key;
            }
        }
        System.out.println();
        System.out.println("Самое частое количество повторений " + maxFgeg + " ->" + " [встретилось " + maxSize + " раз]");
        sizeToFreq.remove(maxFgeg);
        System.out.println("Другие размеры: ");

        int value = 0;
        for (Integer key : sizeToFreq.keySet()) {
            value = sizeToFreq.get(key);

            System.out.println(key + " ->" + value + " раз ");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int quantity(String str, char count) {
        int ch = 0;
        for (char c : str.toCharArray()) {
            if (c == count) {
                ch++;
            }
        }
        return ch;
    }
}

