import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tarea1 {
    public static void main(String[] args) {
        List<int[]> aristas = Arrays.asList(
                new int[]{1, 2}, new int[]{1, 3}, new int[]{2, 4},
                new int[]{3, 10}, new int[]{4, 10}, new int[]{5, 7},
                new int[]{5, 6}, new int[]{6, 9}, new int[]{8, 9}
        );

        GND grafo = new GND(10, aristas);

        System.out.println("Grafo:");
        grafo.mostrarGrafo();

        System.out.println("Vecinos de 9: " + grafo.vecinos(9));

        List<Integer> s1 = Arrays.asList(1, 2, 4, 10);
        List<Integer> s2 = Arrays.asList(2, 4, 5, 7);

        System.out.println("esUnCamino(s1): " + grafo.esUnCamino(s1)); // V
        System.out.println("esUnCamino(s2): " + grafo.esUnCamino(s2)); // F
    }
}

//Primera Parte
class BitMap {
    private int[] b;
    private int size;

    public BitMap(int size) {
        this.size = size;
        int ni = (int) Math.ceil(size / 32);
        b = new int[ni];
    }

    public void On(int i) {
        int index = i / 32;
        int bit = i % 32;
        b[index] |= (1 << bit);
    }

    public void Off(int i) {
        int index = i / 32;
        int bit = i % 32;
        b[index] &= ~(1 << bit);
    }

    public byte Access(int i) {
        int index = i / 32;
        int bit = i % 32;
        return (byte) ((b[index] >> bit) & 1);
    }

    public int Rank(int i) {
        int total = 0;
        for (int k = 0; k < i; k++) {
            total += Access(k);
        }
        return total;
    }

    public int Select(int j) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (Access(i) == 1) {
                count++;
                if (count == j) return i;
            }
        }
        return -1;
    }
}

// Segunda Parte
class GND {
    private int n;
    private BitMap[] matriz;

    public GND(int n, List<int[]> arista) {
        this.n = n;
        matriz = new BitMap[n + 1];
        for (int i = 1; i <= n; i++) {
            matriz[i] = new BitMap(n + 1);
        }

        for (int[] a : arista) {
            int u = a[0], v = a[1];
            matriz[u].On(v);
            matriz[v].On(u);
        }
    }

    public List<Integer> vecinos(int nodo) {
        List<Integer> v = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (matriz[nodo].Access(i) == 1) {
                v.add(i);
            }
        }
        return v;
    }

    public boolean esUnCamino(List<Integer> S) {
        for (int i = 0; i < S.size() - 1; i++) {
            int u = S.get(i), v = S.get(i + 1);
            if (matriz[u].Access(v) == 0) return false;
        }
        return true;
    }

    public void mostrarGrafo() {
        for (int i = 1; i <= n; i++) {
            System.out.print(i + " -> ");
            List<Integer> vecinos = vecinos(i);
            System.out.println(vecinos);
        }
    }
}


