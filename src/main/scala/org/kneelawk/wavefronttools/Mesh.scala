package org.kneelawk.wavefronttools

trait MeshContext {
  def getVertices: Seq[Vec3d]
  def getTextureCoordinates: Seq[Vec2d]
  def getVertexNormals: Seq[Vec3d]
  def getMeshes: Seq[Mesh]
  def transform(m: Mat4d)
}

trait Mesh {
  def getName: String
  def getFaces: Seq[Face]
}

trait Face {
  def getVertices: Seq[Vertex]
}

trait Vertex {
  def getPositionIndex: Int
  def getTextureCoordinateIndex: Int
  def getVertexNormalIndex: Int
}