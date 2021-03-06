package org.kneelawk.wavefronttools

import java.io.OutputStream
import java.io.PrintStream
import java.io.Closeable

class ObjWriter(stream: OutputStream) extends Closeable {
  protected val out = new PrintStream(stream, true)

  def writeVertex(vec: Vec3d) {
    out.println("v " + vec.x + " " + vec.y + " " + vec.z)
  }

  def writeTextureCoordinate(vec: Vec2d) {
    out.println("vt " + vec.x + " " + vec.y)
  }

  def writeNormal(vec: Vec3d) {
    out.println("vn " + vec.x + " " + vec.y + " " + vec.z)
  }
  
  def writeObject(name: String) {
    out.println("o " + name)
  }

  def writeFace(face: Face) {
    out.print("f")
    for (vert <- face.getVertices) {
      out.print(" " + vert.getPositionIndex + "/")
      val tex = vert.getTextureCoordinateIndex
      if (tex > 0) out.print(tex)
      out.print("/")
      val norm = vert.getVertexNormalIndex
      if (norm > 0) out.print(norm)
    }
    out.println()
  }

  def writeMesh(mesh: Mesh) {
    writeObject(mesh.getName)
    
    for (face <- mesh.getFaces) {
      writeFace(face)
    }
  }
  
  def writeMeshes(ctx: MeshContext) {
    for (vert <- ctx.getVertices) {
      writeVertex(vert)
    }
    for (tex <- ctx.getTextureCoordinates) {
      writeTextureCoordinate(tex)
    }
    for (norm <- ctx.getVertexNormals) {
      writeNormal(norm)
    }
    for (mesh <- ctx.getMeshes) {
      writeMesh(mesh)
    }
  }

  def close = out.close()
}