package com.example.hwj.mydemo.anim;

/** Created by hwj on 2018/4/26. */
public class Circle {
  int radius, x, y;
  boolean solid;

  public Circle(int x, int y, int radius, boolean solid) {
    this.radius = radius;
    this.x = x;
    this.y = y;
    this.solid = solid;
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getRadius() {
    return radius;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean getSolid() {
    return solid;
  }

  public void setSolid(boolean solid) {
    this.solid = solid;
  }
}
