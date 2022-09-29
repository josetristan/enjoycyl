package com.jtristan.enjoycyl.ui

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ActivityCardServiceSpec extends Specification implements ServiceUnitTest<ActivityCardService>{

    def setup() {
    }

    def cleanup() {
    }

    void "la descripción corta muestra 500 caracteres acabando en una palabra completa"() {
        given: 'más de 500 caracteres'
        def description = '''Desde el 3 hasta el 31 de marzo. Segunda exposición de fotografía por los alumnos del Colegio de Educación Especial 'Príncipe Don Juan' de Ávila. Coincidiendo con el 10 aniversario de la apertura de su centro, los alumnos han querido compartir con todos nosotros su día a día en el colegio. Gracias al programa de educación responsable de la fundación Botín, con el que han explorado en el mundo del arte y las emocines, han hecho este trabajo que ahora nos muestran, y que con gran emoción siguen través de esta retrospectiva.'''
        def expected = '''Desde el 3 hasta el 31 de marzo. Segunda exposición de fotografía por los alumnos del Colegio de Educación Especial 'Príncipe Don Juan' de Ávila. Coincidiendo con el 10 aniversario de la apertura de su centro, los alumnos han querido compartir con todos nosotros su día a día en el colegio. Gracias al programa de educación responsable de la fundación Botín, con el que han explorado en el mundo del arte y las emocines, han hecho este trabajo que ahora nos muestran, y que con gran emoción siguen  ...'''
        when:
        def shortDesc = service.getShortDescription(description)
        then:
        shortDesc.trim()==expected.trim()

    }
}
