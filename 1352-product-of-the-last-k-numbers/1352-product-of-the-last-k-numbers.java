import java.util.ArrayList;

class ProductOfNumbers {

    ArrayList<Integer> list = new ArrayList<>();

    public ProductOfNumbers() {
        list.add(1);
    }

    public void add(int num) {

        if (num == 0) {
            list.clear();
            list.add(1);
        } else {
            int last = list.get(list.size() - 1);
            list.add(last * num);
        }
    }

    public int getProduct(int k) {

        int n = list.size();

        if (k >= n) {
            return 0;
        }

        return list.get(n - 1) / list.get(n - 1 - k);
    }
}