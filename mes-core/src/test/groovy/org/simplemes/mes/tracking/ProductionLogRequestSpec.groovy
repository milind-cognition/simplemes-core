package org.simplemes.mes.tracking

import org.simplemes.eframe.test.BaseSpecification
import spock.lang.Unroll

/*
 * Copyright Michael Houston. All rights reserved.
 *
*/

/**
 * Tests for ProductionLogRequest.
 */
class ProductionLogRequestSpec extends BaseSpecification {

  def "test default values"() {
    when: 'a request is created with no values'
    def request = new ProductionLogRequest()

    then: 'the defaults are set correctly'
    request.dateTime != null
    request.startDateTime != null
    request.elapsedTime == 0
    request.qty == 0.0
    request.qtyStarted == 0.0
    request.qtyCompleted == 0.0
  }

  def "test field assignments"() {
    given: 'a request with values'
    def request = new ProductionLogRequest(
      action: 'COMPLETE',
      dateTime: new Date(),
      startDateTime: new Date(System.currentTimeMillis() - 1000),
      elapsedTime: 1000L,
      qty: 10.5,
      qtyStarted: 5.0,
      qtyCompleted: 10.0
    )

    expect: 'the values are set correctly'
    request.action == 'COMPLETE'
    request.elapsedTime == 1000L
    request.qty == 10.5
    request.qtyStarted == 5.0
    request.qtyCompleted == 10.0
  }

  @Unroll
  def "test toString includes field names - #field"() {
    given: 'a request with values'
    def request = new ProductionLogRequest(action: 'COMPLETE', qty: 10.0)

    when: 'toString is called'
    def s = request.toString()

    then: 'the field name is in the string'
    s.contains(field)

    where:
    field << ['action', 'qty']
  }

  def "test nullable fields can be null"() {
    when: 'a request is created with nullable fields as null'
    def request = new ProductionLogRequest(
      action: 'COMPLETE',
      user: null,
      order: null,
      lsn: null,
      product: null,
      masterRouting: null,
      operationSequence: null,
      workCenter: null
    )

    then: 'the request is valid'
    request.action == 'COMPLETE'
    request.user == null
    request.order == null
    request.lsn == null
    request.product == null
    request.masterRouting == null
    request.operationSequence == null
    request.workCenter == null
  }

  def "test operation sequence can be set"() {
    when: 'a request is created with operation sequence'
    def request = new ProductionLogRequest(
      action: 'COMPLETE',
      operationSequence: 10
    )

    then: 'the operation sequence is set'
    request.operationSequence == 10
  }

}
