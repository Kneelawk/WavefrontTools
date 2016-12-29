package org.kneelawk.wavefronttools

import scala.collection.mutable.ListBuffer

class BasicMeshContext(
    val vertices: ListBuffer[Vec3d],
    val textureCoords: ListBuffer[Vec2d],
    val normals: ListBuffer[Vec3d],
    val meshes: ListBuffer[BasicMesh]) extends MeshContext {

  def this() = this(new ListBuffer[Vec3d],
    new ListBuffer[Vec2d],
    new ListBuffer[Vec3d],
    new ListBuffer[BasicMesh])

  def addVertex(vert: Vec3d): Int = {
    vertices += vert
    vertices.length
  }

  def addTexCoord(tex: Vec2d): Int = {
    textureCoords += tex
    textureCoords.length
  }

  def addNormal(norm: Vec3d): Int = {
    normals += norm
    normals.length
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
  override def clone = {
    new BasicMeshContext(
      vertices.clone(),
      textureCoords.clone(),
      normals.clone(),
      meshes.map(_.clone))
  }

  def transform(m: Mat4d) = {
    val n = clone()
    n.vertices.transform { x => (m * x.toVec4d(true)).toVec3d }
    n
  }

  def merge(mesh: BasicMeshContext): BasicMeshContext = {
    val newPositions = vertices.clone()
    val positionIndexMap = mesh.vertices.map { x =>
      val i = vertices.indexOf(x)
      if (i > -1) {
        i + 1
      } else {
        newPositions += x
        newPositions.length
      }
    }

    val newTexCoords = textureCoords.clone()
    val texCoordIndexMap = mesh.textureCoords.map { x =>
      val i = textureCoords.indexOf(x)
      if (i > -1) {
        i + 1
      } else {
        newTexCoords += x
        newTexCoords.length
      }
    }

    val newVertNorms = normals.clone()
    val vertNormIndexMap = mesh.normals.map { x =>
      val i = normals.indexOf(x)
      if (i > -1) {
        i + 1
      } else {
        newVertNorms += x
        newVertNorms.length
      }
    }

    new BasicMeshContext(newPositions, newTexCoords, newVertNorms, meshes ++ (mesh.meshes.map { m =>
      new BasicMesh(m.name, m.faces.map { f =>
        new BasicFace(f.vertices.map { v =>
          new BasicVertex(positionIndexMap(v.pos - 1), texCoordIndexMap(v.tex - 1), vertNormIndexMap(v.norm - 1))
        })
      })
    }))
  }

  def +(mesh: BasicMeshContext) = merge(mesh)
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

  def add(pos: Int, tex: Int, norm: Int): BasicFace = {
    vertices += new BasicVertex(pos, tex, norm)
    this
  }

  def getVertices = vertices

  override def clone = new BasicFace(vertices.clone())
}

case class BasicVertex(pos: Int, tex: Int, norm: Int) extends Vertex {
  def getPositionIndex = pos
  def getTextureCoordinateIndex = tex
  def getVertexNormalIndex = norm
}
