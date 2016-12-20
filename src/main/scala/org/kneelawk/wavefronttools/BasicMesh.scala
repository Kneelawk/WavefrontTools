package org.kneelawk.wavefronttools

import scala.collection.mutable.ListBuffer

class BasicMesh(val name: String,
    val vertices: ListBuffer[Vec3d],
    val textureCoords: ListBuffer[Vec2d],
    val normals: ListBuffer[Vec3d],
    val faces: ListBuffer[Face]) extends Mesh {

  def this(name: String) = this(name,
    new ListBuffer[Vec3d],
    new ListBuffer[Vec2d],
    new ListBuffer[Vec3d],
    new ListBuffer[Face])

  def addVertex(vert: Vec3d): BasicMesh = {
    vertices += vert
    this
  }

  def addTexCoord(tex: Vec2d): BasicMesh = {
    textureCoords += tex
    this
  }

  def addNormal(norm: Vec3d): BasicMesh = {
    normals += norm
    this
  }

  def addFace(face: Face): BasicMesh = {
    faces += face
    this
  }

  def getName = name
  def getVertices = vertices
  def getTextureCoordinates = textureCoords
  def getVertexNormals = normals
  def getFaces = faces

  def transform(m: Mat4d) = new BasicMesh(name,
      vertices.map(x => (m * x.toVec4d(true)).toVec3d),
      textureCoords.clone(), normals.clone(), faces.clone())
}

class BasicFace extends Face {
  val vertices = new ListBuffer[Vertex]

  def add(vert: Vertex): BasicFace = {
    vertices += vert
    this
  }

  def add(pos: Int, tex: Int, norm: Int): BasicFace = {
    vertices += new BasicVertex(pos, tex, norm)
    this
  }

  def getVertices = vertices
}

case class BasicVertex(pos: Int, tex: Int, norm: Int) extends Vertex {
  def getPositionIndex = pos
  def getTextureCoordinateIndex = tex
  def getVertexNormalIndex = norm
}
