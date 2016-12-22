package org.kneelawk.wavefronttools

import scala.collection.mutable.ListBuffer

class BasicMeshContext(
    val vertices: ListBuffer[Vec3d],
    val textureCoords: ListBuffer[Vec2d],
    val normals: ListBuffer[Vec3d],
    val meshes: ListBuffer[Mesh]) extends MeshContext {

  def this() = this(new ListBuffer[Vec3d],
    new ListBuffer[Vec2d],
    new ListBuffer[Vec3d],
    new ListBuffer[Mesh])

  def addVertex(vert: Vec3d): PositionIndex = {
    vertices += vert
    new PositionIndex(vertices.length)
  }

  def addTexCoord(tex: Vec2d): TexCoordIndex = {
    textureCoords += tex
    new TexCoordIndex(textureCoords.length)
  }

  def addNormal(norm: Vec3d): VertNormIndex = {
    normals += norm
    new VertNormIndex(normals.length)
  }

  def addMesh(mesh: Mesh): BasicMeshContext = {
    meshes += mesh
    this
  }

  def getVertices = vertices
  def getTextureCoordinates = textureCoords
  def getVertexNormals = normals
  def getMeshes = meshes

  def transform(m: Mat4d) = new BasicMeshContext(
    vertices.map(x => (m * x.toVec4d(true)).toVec3d),
    textureCoords.clone(), normals.clone(), meshes.clone())
}

class BasicMesh(val name: String,
    val faces: ListBuffer[Face]) extends Mesh {

  def this(name: String) = this(name,
    new ListBuffer[Face])

  def addFace(face: Face): BasicMesh = {
    faces += face
    this
  }

  def getName = name
  def getFaces = faces
}

class BasicFace extends Face {
  val vertices = new ListBuffer[Vertex]

  def add(vert: Vertex): BasicFace = {
    vertices += vert
    this
  }

  def add(pos: PositionIndex, tex: TexCoordIndex, norm: VertNormIndex): BasicFace = {
    vertices += new BasicVertex(pos, tex, norm)
    this
  }

  def getVertices = vertices
}

case class BasicVertex(pos: PositionIndex, tex: TexCoordIndex, norm: VertNormIndex) extends Vertex {
  def getPositionIndex = pos.index
  def getTextureCoordinateIndex = tex.index
  def getVertexNormalIndex = norm.index
}

case class PositionIndex(index: Int)
case class TexCoordIndex(index: Int)
case class VertNormIndex(index: Int)
