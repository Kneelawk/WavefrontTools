package org.kneelawk.wavefronttools

case class Vec3d(x: Double, y: Double, z: Double) {
  val xyTheta: Double = Math.atan2(y, x)
  val xyR: Double = Math.sqrt(x * x + y * y)
  val zTheta: Double = Math.atan2(z, xyR)
  val r: Double = Math.sqrt(x * x + y * y + z * z)

  def dot(m: Double): Vec3d = new Vec3d(x * m, y * m, z * m)
  def dot(m: Vec3d): Double = x * m.x + y * m.y + z * m.z
  def cross(m: Vec3d): Vec3d = new Vec3d(y * m.z - z * m.y, z * m.x - x * m.z, x * m.y - y * m.x)
  def normalize: Vec3d = {
    if (r == 0) new Vec3d(0, 0, 0)
    else new Vec3d(x / r, y / r, z / r)
  }
}