#!/usr/bin/env bash

BASEDIR=$(dirname "$0")

	host=$(/sbin/ip -o -4 addr list eth0 | awk '{print $4}' | cut -d/ -f1)
	remoto=$1
	atletas=$2
	reinicio=$3
	numMaxAtletas=$4

	echo $host

	echo "Lanzando proceso en mAquina $host"
	ssh $remoto "rm carpetaRemota/*; rmdir carpetaRemota; mkdir carpetaRemota; exit"
	
	scp $BASEDIR/Carrera100m.war $remoto:carpetaRemota

	ssh $remoto "cd carpetaRemota; jar -xvf Carrera100m.war; cd WEB-INF;java -classpath \"lib/*:classes/.\" servicios.IniciarAtleta " $host $atletas $reinicio $numMaxAtletas

