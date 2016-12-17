package org.kneelawk.wavefronttools

import scala.collection.mutable.ListBuffer

class BasicMesh(val name: String) extends Mesh {
  val vertices = new ListBuffer[Vec3d]
  val textureCoords = new ListBuffer[Vec2d]
  val normals = new ListBuffer[Vec3d]
  val faces = new ListBuffer[Face]

  def addVertex(vert: Vec3d): BasicMesh = {
    vertices += vert
    this
  }
  
  def addTextCoord(tex: Vec2d): BasicMesh = {
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
