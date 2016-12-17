package org.kneelawk.wavefronttools

trait Mesh {
  def getName: String
  def getVertices: Seq[Vec3d]
  def getTextureCoordinates: Seq[Vec2d]
  def getVertexNormals: Seq[Vec3d]
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