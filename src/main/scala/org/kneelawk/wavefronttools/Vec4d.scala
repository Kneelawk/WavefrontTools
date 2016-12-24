package org.kneelawk.wavefronttools

case class Vec4d(x: Double, y: Double, z: Double, w: Double) {
  def toVec3d = new Vec3d(x, y, z)

  override def equals(a: Any): Boolean =
    if (a.isInstanceOf[Vec4d]) {
      val vec = a.asInstanceOf[Vec4d]
      x == vec.x && y == vec.y && z == vec.z && w == vec.w
    } else false
}