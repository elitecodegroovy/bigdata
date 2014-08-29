package com.bigdata.processing

import java.net.{ServerSocket, Socket}
import java.util.concurrent.{ExecutorService, Executors}

/**
 * @author Created by JohnLiu on 2014/8/1.
 * @version 0.1.0
 */
class NetworkService(port: Int, poolSize: Int) extends Runnable {
  val serverSocket = new ServerSocket(port)
  val pool: ExecutorService = Executors.newFixedThreadPool(poolSize)

  def run() {
    println("" + System.currentTimeMillis() + "--" + Thread.currentThread().getName())
    try {
      while (true) {
        // This will block until a connection comes in.
        val socket = serverSocket.accept()
        println("serverSocket.accept():" + System.currentTimeMillis() + "--" + Thread.currentThread().getName())
        pool.execute(new Handler(socket))
      }
    } finally {
      pool.shutdown()
    }
  }
}

class Handler(socket: Socket) extends Runnable {
  def message = (Thread.currentThread.getName() + "\n").getBytes

  def run() {
    socket.getOutputStream.write(message)
    socket.getOutputStream.close()
  }
}
