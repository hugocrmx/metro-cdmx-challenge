package com.code.metro.business;

import java.util.*;

public class ShortestPath {
  public int[][] getShortestPath() {
    return shortestPath;
  }

  public void setShortestPath(int[][] shortestPath) {
    this.shortestPath = shortestPath;
  }

  public int[][] getShortestDis() {
    return shortestDis;
  }

  public void setShortestDis(int[][] shortestDis) {
    this.shortestDis = shortestDis;
  }

  public static int getMax() {
    return max;
  }

  private static final int max = 999999;
  private int[][] shortestPath;
  private int[][] shortestDis;

  public ShortestPath(int[][] G) {
    this.shortestPath = new int[G.length][G.length];
    this.shortestDis = new int[G.length][G.length];
    for(int i=0;i<G.length;i++) {
      for(int j=0;j<G.length;j++) {
        this.shortestPath[i][j]=j;
        this.shortestDis[i][j]=G[i][j];
        this.shortestDis[j][i]=G[j][i];
      }
    }

    //Floyd
    for(int k=0;k<G.length;k++)
      for(int j=0;j<G.length;j++)
        for(int i=0;i<G.length;i++) {
          if(this.shortestDis[i][j]>this.shortestDis[i][k]+this.shortestDis[k][j]) {
            this.shortestDis[i][j]=this.shortestDis[i][k]+this.shortestDis[k][j];
            this.shortestPath[i][j]=this.shortestPath[i][k];
          }
        }
  }

  public int getMinDis(int i, int j) {
    return this.shortestDis[i][j];
  }

  public List<Integer> indexToList(int i, int j){
    List<Integer> list = new ArrayList<>();
    while(i!=j) {
      list.add(i);
      i = this.shortestPath[i][j];
    }
    list.add(i);
    return list;
  }
}

