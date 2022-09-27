import java.util.Arrays;

import edu.princeton.cs.algs4.Queue;

public class Board {
    private int[][] board;
    private int size;
    private int manhattan;
    private int hamming;

    public Board(int[][] board) {
        this.size = board.length;
        this.board = new int[size][size];
        for (int i = 0; i < size; ++ i) {
            this.board[i] = Arrays.copyOf(board[i], size);
        }
        for (int i = 0; i < this.size; ++ i) {
            for (int j = 0; j < this.size; ++ j) {
                if (this.board[i][j] != 0) {
                    int current = this.board[i][j];
                    this.manhattan += Math.abs((current - 1) / this.size - i);
                    this.manhattan += Math.abs((current - 1) % this.size - j);
                    this.hamming += (this.board[i][j] != i * this.size + j + 1 ? 1 : 0);
                }
            }
        }
    }

    @Override
    public String toString() {
        String res = this.size + "\n";
        for (int[] u : this.board) {
            for (int v: u) {
                res = res + v + " ";
            }
            res += '\n';
        }
        return res;
    }

    public int dimension() {
        return this.size;
    }

    public int hamming() {
        return this.hamming;
    }

    public int manhattan() {
        return this.manhattan;
    }

    public boolean isGoal() {
        return this.hamming() == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Board)) {
            return false;
        }
        Board that = (Board) obj;
        int size = that.dimension();
        if (size != this.size) {
            return false;
        }
        for (int i = 0; i < size; ++ i) {
            for (int j = 0; j < size; ++ j) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> qBoard = new Queue<>();
        for (int i = 0; i < this.size; ++ i) {
            for (int j = 0; j < this.size; ++j) {
                if(this.board[i][j] == 0) {
                    if(i > 0) {
                        qBoard.enqueue(boardAfterSwap(i, j, i - 1, j));
                    }
                    if(j > 0) {
                        qBoard.enqueue(boardAfterSwap(i, j, i, j - 1));
                    }
                    if(i < this.size - 1) {
                        qBoard.enqueue(boardAfterSwap(i, j, i + 1, j));
                    }
                    if(j < this.size - 1) {
                        qBoard.enqueue(boardAfterSwap(i, j, i, j + 1));
                    }
                }
            }
        }
        return qBoard;
    }

    public Board twin() {
        int x = 0, y = 0, u = 1, v = 1;
        if(this.board[x][y] == 0) {
            x = 1;
        }
        if(this.board[u][v] == 0) {
            v = 0;
        }
        return boardAfterSwap(x, y, u, v);
    }

    public static void main(String[] args) {

    }

    private Board boardAfterSwap(int x, int y, int u, int v) {
        int[][] newBoard = new int[this.size][this.size];
        for (int i = 0; i < this.size; ++ i) {
            for (int j = 0; j < this.size; ++ j) {
                if (i == x && j == y) {
                    newBoard[i][j] = this.board[u][v];
                } else if (i == u && j == v) {
                    newBoard[i][j] = this.board[x][y];
                } else {
                    newBoard[i][j] = this.board[i][j];
                }
            }
        }
        return new Board(newBoard);
    }

}
