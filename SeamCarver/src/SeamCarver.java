import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private double[][] energy;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        if(picture == null) {
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
        this.width = this.picture.width();
        this.height = this.picture.height();
        energy = new double[this.width][this.height];
        for(int i = 0; i < this.width; ++ i) {
            for(int j = 0; j < this.height; ++ j) {
                this.energy[i][j] = Calc(this.picture, i, j);
            }
        }
    }

    public Picture picture() {
        return new Picture(this.picture);
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public double energy(int x, int y) {
        if(x < 0 || y < 0 || x >= this.width || y >= this.height) {
            throw new IllegalArgumentException();
        }
        return this.energy[x][y];
    }

    public int[] findHorizontalSeam() {
        int[][] trace = new int[this.width][this.height];
        int[] ans = new int[this.width];
        double[][] dp = new double[this.width][this.height];
        for(int i = 0; i < this.width; ++ i) {
            for(int j = 0; j < this.height; ++ j) {
                dp[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for(int i = 0; i < this.height; ++ i) {
            dp[0][i] = 1000.0;
        }
        for(int i = 0; i < this.width - 1; ++ i) {
            for(int j = 0; j < this.height; ++ j) {
                if(j > 0 && dp[i + 1][j - 1] > dp[i][j] + this.energy(i + 1, j - 1)) {
                    dp[i + 1][j - 1] = dp[i][j] + this.energy(i + 1, j - 1);
                    trace[i + 1][j - 1] = j;
                }
                if(j + 1 < this.height && dp[i + 1][j + 1] > dp[i][j] + this.energy(i + 1, j + 1)) {
                    dp[i + 1][j + 1] = dp[i][j] + this.energy(i + 1, j + 1);
                    trace[i + 1][j + 1] = j;
                }
                if(dp[i + 1][j] > dp[i][j] + this.energy(i + 1, j)) {
                    dp[i + 1][j] = dp[i][j] + this.energy(i + 1, j);
                    trace[i + 1][j] = j;
                }
            }
        }
        double res = Double.POSITIVE_INFINITY;
        for(int i = 0; i < this.height; ++ i) {
            if(res > dp[this.width - 1][i]) {
                res = dp[this.width - 1][i];
                ans[this.width - 1] = i;
            }
        }
        for(int i = this.width - 2; i >= 0; -- i) {
            ans[i] = trace[i + 1][ans[i + 1]];
        }
        return ans;
    }

    public int[] findVerticalSeam() {
        int[][] trace = new int[this.width][this.height];
        int[] ans = new int[this.height];
        double[][] dp = new double[this.width][this.height];
        for(int i = 0; i < this.width; ++ i) {
            for(int j = 0; j < this.height; ++ j) {
                dp[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for(int i = 0; i < this.width; ++ i) {
            dp[i][0] = 1000.0;
        }
        for(int j = 0; j < this.height - 1; ++ j) {
            for(int i = 0; i < this.width; ++ i) {
                if(i > 0 && dp[i - 1][j + 1] > dp[i][j] + this.energy(i - 1, j + 1)) {
                    dp[i - 1][j + 1] = dp[i][j] + this.energy(i - 1, j + 1);
                    trace[i - 1][j + 1] = i;
                }
                if(i + 1 < this.width && dp[i + 1][j + 1] > dp[i][j] + this.energy(i + 1, j + 1)) {
                    dp[i + 1][j + 1] = dp[i][j] + this.energy(i + 1, j + 1);
                    trace[i + 1][j + 1] = i;
                }
                if(dp[i][j + 1] > dp[i][j] + this.energy(i, j + 1)) {
                    dp[i][j + 1] = dp[i][j] + this.energy(i, j + 1);
                    trace[i][j + 1] = i;
                }
            }
        }
        double res = Double.POSITIVE_INFINITY;
        for(int i = 0; i < this.width; ++ i) {
            if(res > dp[i][this.height - 1]) {
                res = dp[i][this.height - 1];
                ans[this.height - 1] = i;
            }
        }
        for(int i = this.height - 2; i >= 0; -- i) {
            ans[i] = trace[ans[i + 1]][i + 1];
        }
        return ans;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < seam.length; ++i) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        if (this.height() <= 1 || seam.length != this.width) {
            throw new IllegalArgumentException();
        }
        this.height--;
        Picture tempPicture = new Picture(this.width, this.height);
        for (int i = 0; i < this.width; ++i) {
            for (int j = 0; j < seam[i]; ++j) {
                tempPicture.set(i, j, this.picture.get(i, j));
            }
            for (int j = seam[i]; j < this.height; ++j) {
                tempPicture.set(i, j, this.picture.get(i, j + 1));
            }
        }
        this.picture = tempPicture;
        for(int i = 0; i < this.width; ++ i) {
            for(int j = 0; j < this.height; ++ j) {
                this.energy[i][j] = Calc(this.picture, i, j);
            }
        }
    }


    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < seam.length; ++ i) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        if(this.width <= 1 || seam.length != this.height) {
            throw new IllegalArgumentException();
        }
        this.width--;
        Picture tempPicture = new Picture(this.width, this.height);
        for(int i = 0; i < this.height; ++ i) {
            for(int j = 0; j < seam[i]; ++ j) {
                tempPicture.set(j, i, this.picture.get(j, i));
            }
            for(int j = seam[i] ; j < this.width; ++ j) {
                tempPicture.set(j, i, this.picture.get(j + 1, i));
            }
        }
        this.picture = tempPicture;
        for(int i = 0; i < this.width; ++ i) {
            for(int j = 0; j < this.height; ++ j) {
                this.energy[i][j] = Calc(this.picture, i, j);
            }
        }
    }

    public static void main(String[] args) {
        Picture picture = new Picture("seam/4x6.png");
        SeamCarver seam = new SeamCarver(picture);
        int[] a = seam.findVerticalSeam();
        for (int i = 0; i < a.length; ++ i) {
            System.out.print(a[i] + " ");
        }
    }

    private double Calc(Picture picture, int i, int j) {
        if (i == 0 || i == this.width - 1 || j == 0 || j == this.height - 1) {
            return 1000.0;
        }
        Color lx = picture.get(i - 1, j);
        Color rx = picture.get(i + 1, j);
        Color uy = picture.get(i, j - 1);
        Color dy = picture.get(i, j + 1);
        double bEx = (rx.getBlue() - lx.getBlue());
        double rEx = (rx.getRed() - lx.getRed());
        double gEx = (rx.getGreen() - lx.getGreen());
        double bEy = (dy.getBlue() - uy.getBlue());
        double rEy = (dy.getRed() - uy.getRed());
        double gEy = (dy.getGreen() - uy.getGreen());
        return Math.sqrt(bEx * bEx + bEy * bEy + rEx * rEx + rEy * rEy + gEx * gEx + gEy * gEy);
    }

}