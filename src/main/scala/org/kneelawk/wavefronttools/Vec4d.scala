package org.kneelawk.wavefronttools

case class Vec4d(x: Double, y: Double, z: Double, w: Double) {
  def toVec3d = new Vec3d(x, y, z)
}