package org.kneelawk.wavefronttools

import scala.collection.mutable.ListBuffer

class BasicMeshContext(
    val vertices: ListBuffer[Vec3d],
    val textureCoords: ListBuffer[Vec2d],
    val normals: ListBuffer[Vec3d],
    val meshes: ListBuffer[BasicMesh]) extends MeshContext {

  val positionIndices = new ListBuffer[PositionIndex]
  val texCoordIndices = new ListBuffer[TexCoordIndex]
  val vertNormIndices = new ListBuffer[VertNormIndex]

  def this() = this(new ListBuffer[Vec3d],
    new ListBuffer[Vec2d],
    new ListBuffer[Vec3d],
    new ListBuffer[BasicMesh])

  def addVertex(vert: Vec3d): PositionIndex = {
    vertices += vert
    val idx = new PositionIndex(vertices.length)
    positionIndices += idx
    idx
  }

  def addTexCoord(tex: Vec2d): TexCoordIndex = {
    textureCoords += tex
    val idx = new TexCoordIndex(textureCoords.length)
    texCoordIndices += idx
    idx
  }

  def addNormal(norm: Vec3d): VertNormIndex = {
    normals += norm
    val idx = new VertNormIndex(normals.length)
    vertNormIndices += idx
    idx
  }

  def addMesh(mesh: BasicMesh): BasicMeshContext = {
    meshes += mesh
    this
  }

  def getVertices = vertices
  def getTextureCoordinates = textureCoords
  def getVertexNormals = normals
  def getMeshes = meshes

  /**
   * BasicMeshContexts are mutable
   */
  override def clone = new BasicMeshContext(
    vertices.clone(),
    textureCoords.clone(),
    normals.clone(),
    meshes.map(_.clone))

  def transform(m: Mat4d): BasicMeshContext = {
    vertices.transform(x => (m * x.toVec4d(true)).toVec3d)
    this
  }

  def merge(mesh: BasicMeshContext) {
    // TODO: squash duplicates
    vertices ++= mesh.vertices
    textureCoords ++= mesh.textureCoords
    normals ++= mesh.normals
    mesh.positionIndices.foreach { x => x.index += positionIndices.length }
    mesh.texCoordIndices.foreach { x => x.index += texCoordIndices.length }
    mesh.vertNormIndices.foreach { x => x.index += vertNormIndices.length }
    positionIndices ++= mesh.positionIndices
    texCoordIndices ++= mesh.texCoordIndices
    vertNormIndices ++= mesh.vertNormIndices
  }
}

class BasicMesh(val name: String,
    val faces: ListBuffer[BasicFace]) extends Mesh {

  def this(name: String) = this(name,
    new ListBuffer[BasicFace])

  def addFace(face: BasicFace): BasicMesh = {
    faces += face
    this
  }

  def getName = name
  def getFaces = faces

  override def clone = new BasicMesh(name, faces.map(_.clone))
}

class BasicFace(val vertices: ListBuffer[BasicVertex]) extends Face {

  def this() = this(new ListBuffer[BasicVertex])

  def add(vert: BasicVertex): BasicFace = {
    vertices += vert
    this
  }

  def add(pos: PositionIndex, tex: TexCoordIndex, norm: VertNormIndex): BasicFace = {
    vertices += new BasicVertex(pos, tex, norm)
    this
  }

  def getVertices = vertices

  override def clone = new BasicFace(vertices.map(_.clone))
}

case class BasicVertex(pos: PositionIndex, tex: TexCoordIndex, norm: VertNormIndex) extends Vertex {
  def getPositionIndex = pos.index
  def getTextureCoordinateIndex = tex.index
  def getVertexNormalIndex = norm.index

  override def clone = new BasicVertex(pos.clone, tex.clone, norm.clone)
}

case class PositionIndex(var index: Int) {
  override def clone = new PositionIndex(index)
}
case class TexCoordIndex(var index: Int) {
  override def clone = new TexCoordIndex(index)
}
case class VertNormIndex(var index: Int) {
  override def clone = new VertNormIndex(index)
}
