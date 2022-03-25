#!/usr/bin/env bash

BASEDIR=$(dirname "$0")

	host= $(hostname -I)
	remoto=$1
	atletas=$2
	reinicio=$3
	numMaxAtletas=$4

	echo "Lanzando proceso en mAquina $host"
	ssh $remoto "rm carpetaRemota/*; rmdir carpetaRemota; mkdir carpetaRemota; exit"
	
	scp "Carrera100m.war $remoto:carpetaRemota"

	ssh $remoto "cd carpetaRemota; jar -xvf Carrera100m.war; cd WEB-INF; java -classpath \"lib/*:classes/.\" servicios.IniciarAtleta" $host $atletas $reinicio $numMaxAtletas
