/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.toolbox.math;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class VectorMath {

    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    public static Vector3f add(Vector3f original, Vector3f addition) {
        return new Vector3f(
                original.x + addition.x,
                original.y + addition.y,
                original.z + addition.z);
    }

    public static Vector2f add(Vector2f original, Vector2f addition) {
        return new Vector2f(
                original.x + addition.x,
                original.y + addition.y);
    }

    public static Vector3f multiply(Vector3f original, Vector3f multiplication) {
        return new Vector3f(
                original.x * multiplication.x,
                original.y * multiplication.y,
                original.z * multiplication.z);
    }

    public static Vector2f multiply(Vector2f original, Vector2f multiplication) {
        return new Vector2f(
                original.x * multiplication.x,
                original.y * multiplication.y);
    }

    public static Vector3f copyOf(Vector3f original) {
        return new Vector3f(original);
    }

    public static Vector2f copyOf(Vector2f original) {
        return new Vector2f(original);
    }

}
