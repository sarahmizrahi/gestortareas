package org.example.proyecto_sarahmizrahivol2.repository

import org.example.proyecto_sarahmizrahivol2.entity.Tarea
import org.springframework.data.jpa.repository.JpaRepository

interface TareaRepository : JpaRepository<Tarea, Long>