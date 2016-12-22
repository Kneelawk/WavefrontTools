package org.kneelawk.wavefronttools

/**
 * Immutable 4x4 Matrix
 */
case class Mat4d(data: Vector[Vector[Double]]) {

  def this(value: Double) = this(Vector(
    Vector(value, 0, 0, 0),
    Vector(0, value, 0, 0),
    Vector(0, 0, value, 0),
    Vector(0, 0, 0, value)))

  def this() = this(1)

  val i11 = data(0)(0)
  val i12 = data(0)(1)
  val i13 = data(0)(2)
  val i14 = data(0)(3)
  val i21 = data(1)(0)
  val i22 = data(1)(1)
  val i23 = data(1)(2)
  val i24 = data(1)(3)
  val i31 = data(2)(0)
  val i32 = data(2)(1)
  val i33 = data(2)(2)
  val i34 = data(2)(3)
  val i41 = data(3)(0)
  val i42 = data(3)(1)
  val i43 = data(3)(2)
  val i44 = data(3)(3)

  def *(m: Mat4d) = new Mat4d(Vector(
    Vector(
      i11 * m.i11 + i12 * m.i21 + i13 * m.i31 + i14 * m.i41,
      i11 * m.i12 + i12 * m.i22 + i13 * m.i32 + i14 * m.i42,
      i11 * m.i13 + i12 * m.i23 + i13 * m.i33 + i14 * m.i43,
      i11 * m.i14 + i12 * m.i24 + i13 * m.i34 + i14 * m.i44),
    Vector(
      i21 * m.i11 + i22 * m.i21 + i23 * m.i31 + i24 * m.i41,
      i21 * m.i12 + i22 * m.i22 + i23 * m.i32 + i24 * m.i42,
      i21 * m.i13 + i22 * m.i23 + i23 * m.i33 + i24 * m.i43,
      i21 * m.i14 + i22 * m.i24 + i23 * m.i34 + i24 * m.i44),
    Vector(
      i31 * m.i11 + i32 * m.i21 + i33 * m.i31 + i34 * m.i41,
      i31 * m.i12 + i32 * m.i22 + i33 * m.i32 + i34 * m.i42,
      i31 * m.i13 + i32 * m.i23 + i33 * m.i33 + i34 * m.i43,
      i31 * m.i14 + i32 * m.i24 + i33 * m.i34 + i34 * m.i44),
    Vector(
      i41 * m.i11 + i42 * m.i21 + i43 * m.i31 + i44 * m.i41,
      i41 * m.i12 + i42 * m.i22 + i43 * m.i32 + i44 * m.i42,
      i41 * m.i13 + i42 * m.i23 + i43 * m.i33 + i44 * m.i43,
      i41 * m.i14 + i42 * m.i24 + i43 * m.i34 + i44 * m.i44)))

  def *(v: Vec4d) = new Vec4d(
    i11 * v.x + i12 * v.y + i13 * v.z + i14 * v.w,
    i21 * v.x + i22 * v.y + i23 * v.z + i24 * v.w,
    i31 * v.x + i32 * v.y + i33 * v.z + i34 * v.w,
    i41 * v.x + i42 * v.y + i43 * v.z + i44 * v.w)
  
  def *(m: MeshContext) = m.transform(this)
}

object Mat4d {
  def identity = new Mat4d

  def translate(v: Vec4d) = new Mat4d(Vector(
    Vector(1d, 0d, 0d, v.x),
    Vector(0d, 1d, 0d, v.y),
    Vector(0d, 0d, 1d, v.z),
    Vector(0d, 0d, 0d, v.w)))

  def scale(v: Vec4d) = new Mat4d(Vector(
    Vector(v.x, 0d, 0d, 0d),
    Vector(0d, v.y, 0d, 0d),
    Vector(0d, 0d, v.z, 0d),
    Vector(0d, 0d, 0d, v.w)))
}
