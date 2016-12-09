package org.kneelawk.wavefronttools

case class Vec2d(x: Double, y: Double) {
  val theta: Double = Math.atan2(y, x)
  val r: Double = Math.sqrt(x * x + y * y)

  def dot(m: Double): Vec2d = new Vec2d(x * m, y * m)
  def dot(m: Vec2d): Double = x * m.x + y * m.y
  def normalize: Vec2d = {
    if (r == 0) new Vec2d(0, 0)
    else new Vec2d(x / r, y / r)
  }
}