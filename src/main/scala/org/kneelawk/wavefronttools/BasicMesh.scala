package org.kneelawk.wavefronttools

import scala.collection.mutable.ListBuffer

class BasicMeshContext(
    val vertices: ListBuffer[(Vec3d, PositionIndex)],
    val textureCoords: ListBuffer[(Vec2d, TexCoordIndex)],
    val normals: ListBuffer[(Vec3d, VertNormIndex)],
    val meshes: ListBuffer[BasicMesh]) extends MeshContext {

  def this() = this(new ListBuffer[(Vec3d, PositionIndex)],
    new ListBuffer[(Vec2d, TexCoordIndex)],
    new ListBuffer[(Vec3d, VertNormIndex)],
    new ListBuffer[BasicMesh])

  def addVertex(vert: Vec3d): PositionIndex = {
    val idx = new PositionIndex(vertices.length)
    vertices += ((vert, idx))
    idx
  }

  def addTexCoord(tex: Vec2d): TexCoordIndex = {
    val idx = new TexCoordIndex(textureCoords.length)
    textureCoords += ((tex, idx))
    idx
  }

  def addNormal(norm: Vec3d): VertNormIndex = {
    val idx = new VertNormIndex(normals.length)
    normals += ((norm, idx))
    idx
  }

  def addMesh(mesh: BasicMesh): BasicMeshContext = {
    meshes += mesh
    this
  }

  def getVertices = vertices.map(_._1)
  def getTextureCoordinates = textureCoords.map(_._1)
  def getVertexNormals = normals.map(_._1)
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
    vertices.transform(x => ((m * x._1.toVec4d(true)).toVec3d, x._2))
    this
  }

  private def merge[Data, Index <: MeshIndex](mine: ListBuffer[(Data, Index)], other: ListBuffer[(Data, Index)]) {
    val len = mine.length
    other.foreach(e => {
      val i = mine.indexWhere(_._1 == e._1)
      if (i >= 0) {
        e._2.set(mine(i)._2.get)
      } else {
        e._2.offset(len)
        mine += e
      }
    })
  }

  def merge(mesh: BasicMeshContext) {
    merge(vertices, mesh.vertices)
    merge(textureCoords, mesh.textureCoords)
    merge(normals, mesh.normals)
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

trait MeshIndex {
  def get: Int
  def set(i: Int)
  def offset(i: Int) {
    set(get + i)
  }
}
case class PositionIndex(var index: Int) extends MeshIndex {
  override def clone = new PositionIndex(index)
  def get = index
  def set(i: Int) = index = i
}
case class TexCoordIndex(var index: Int) extends MeshIndex {
  override def clone = new TexCoordIndex(index)
  def get = index
  def set(i: Int) = index = i
}
case class VertNormIndex(var index: Int) extends MeshIndex {
  override def clone = new VertNormIndex(index)
  def get = index
  def set(i: Int) = index = i
}
