package org.simplemes.mes.demand

import org.simplemes.eframe.test.BaseSpecification
import org.simplemes.mes.demand.domain.LSN
import org.simplemes.mes.demand.domain.Order
import spock.lang.Unroll

/*
 * Copyright Michael Houston. All rights reserved.
 *
*/

/**
 * Tests for CompleteResponse.
 */
class CompleteResponseSpec extends BaseSpecification {

  def "test default values"() {
    when: 'a response is created with no values'
    def response = new CompleteResponse()

    then: 'the defaults are set correctly'
    response.operationSequence == 0
    response.done == false
    response.allowUndo == true
    response.order == null
    response.lsn == null
    response.qty == null
  }

  def "test field assignments"() {
    given: 'an order and LSN'
    def order = new Order(order: 'M001')
    def lsn = new LSN(lsn: 'L001')

    when: 'a response is created with values'
    def response = new CompleteResponse(
      order: order,
      lsn: lsn,
      operationSequence: 10,
      qty: 100.0,
      done: true,
      allowUndo: false
    )

    then: 'the values are set correctly'
    response.order == order
    response.lsn == lsn
    response.operationSequence == 10
    response.qty == 100.0
    response.done == true
    response.allowUndo == false
  }

  def "test getUndoActions returns undo action when allowUndo is true"() {
    given: 'a response with allowUndo true'
    def response = new CompleteResponse(allowUndo: true)

    when: 'undo actions are requested'
    def undoActions = response.getUndoActions()

    then: 'an undo action is returned'
    undoActions.size() == 1
    undoActions[0] instanceof CompleteUndoAction
  }

  def "test getUndoActions returns empty list when allowUndo is false"() {
    given: 'a response with allowUndo false'
    def response = new CompleteResponse(allowUndo: false)

    when: 'undo actions are requested'
    def undoActions = response.getUndoActions()

    then: 'an empty list is returned'
    undoActions.size() == 0
  }

  @Unroll
  def "test toString includes field names - #field"() {
    given: 'a response with values'
    def response = new CompleteResponse(qty: 10.0, done: true)

    when: 'toString is called'
    def s = response.toString()

    then: 'the field name is in the string'
    s.contains(field)

    where:
    field << ['qty', 'done', 'operationSequence']
  }

  }
