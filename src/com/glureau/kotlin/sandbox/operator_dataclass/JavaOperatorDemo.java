package com.glureau.kotlin.sandbox.operator_dataclass;

public class JavaOperatorDemo {

    static class Vector2d {
        final int x;
        final int y;
        Vector2d(int x, int y) {
            this.x = x;
            this.y = y;
        }
        Vector2d plus(Vector2d other) {
            return new Vector2d(x + other.x, y + other.y);
        }
        Vector2d minus(Vector2d other) {
            return new Vector2d(x - other.x, y - other.y);
        }
        @Override
        public String toString() {
            return "x:" + x + " y:" + y;
        }
        @Override
        public boolean equals(Object obj) {
            return (obj != null) && obj instanceof Vector2d && ((Vector2d) obj).x == x && ((Vector2d) obj).y == y;
        }
    }

    public static void main(String[] args) {
        Vector2d vec1 = new Vector2d(10, 5);
        Vector2d vec2 = new Vector2d(2, 4);
        Vector2d vec3 = new Vector2d(5, 4);
        System.out.println(vec1.plus(vec2).minus(vec3));
        System.out.println(vec1.plus(vec2).minus(vec3).equals(new Vector2d(7, 5)));
    }
}
