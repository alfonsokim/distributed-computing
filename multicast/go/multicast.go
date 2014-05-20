// Implementacion de multicast con UDP
// Computo Distribuido
// Mayo 2014

package main

import (
	"bufio"
	"bytes"
	"flag"
	"fmt"
	"net"
	"os"
)

// ========================================================================

// Lee una cadena del teclado
func fromUser() []byte {
	reader := bufio.NewReader(os.Stdin)
	fmt.Printf("multicast>")
	line, _, err := reader.ReadLine()
	check(err)
	return line
}

// ========================================================================

// Servidor: Captura la entrada del teclado y hace multicast a los clientes
// mientas que el usuario no capture "quit"
func multicastServer(conn *net.UDPConn, address *net.UDPAddr) {
	for str := fromUser(); !bytes.Equal(str, []byte("quit")); str = fromUser() {
		buffer := make([]byte, 512)
		copy(buffer, str)
		_, err := conn.WriteToUDP(buffer, address)
		check(err)
	}
}

// ========================================================================

// Cliente: Escucha de la direccion UDP los mensajes multicast
func listen(conn *net.UDPConn) {
	for {
		b := make([]byte, 256)
		_, _, err := conn.ReadFromUDP(b)
		check(err)
		fmt.Println("server:", string(b))
	}
}

// ========================================================================
// Revisa si hay error
func check(err error) {
	if err != nil {
		panic(err)
	}
}

// ========================================================================

// Punto de entrada a la consola
func main() {
	var ip = flag.String("ip", "224.0.1.60:1888", "Direccion:Puerto IP del multicast")
	var server = flag.Bool("server", false, "Funcionar como servidor")
	flag.Parse()

	localAddress, err := net.ResolveUDPAddr("udp", ":0")
	multicastAddress, err := net.ResolveUDPAddr("udp", *ip)
	check(err)
	multicastConn, err := net.ListenMulticastUDP("udp", nil, multicastAddress)
	localConn, err := net.ListenUDP("udp", localAddress)
	check(err)

	if *server {
		fmt.Printf("Modo servidor [%s]\n", *ip)
		multicastServer(localConn, multicastAddress)
	} else {
		fmt.Printf("Modo cliente [%s]\n", *ip)
		listen(multicastConn)
	}

}
