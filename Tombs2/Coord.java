/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package Toms2;

/**
 *
 * @author steve
 */
/**
 * A class describing a coordinate row,column
 * on a grid.
 */
class Coord implements Comparable<Coord> {
    final int row,column;
    public Coord(int row,int column) {
        this.row = row;
        this.column = column;
    }
    public String toString() {
        return String.format("(%d,%d)",row,column);
    }
    /**
     * Allow it to be compared.
     */
    public boolean equals(Object o) {
        Coord c = (Coord)o;
        return row == c.row && column == c.column;
    }
    /**
     * Allow it to be sorted.
     */
    public int compareTo(Coord that) {
        int diff = this.row - that.row;
        if(diff == 0) diff = this.column - that.column;
        return diff;
    }
}
