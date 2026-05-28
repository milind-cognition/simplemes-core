package org.simplemes.mes.demand

import org.simplemes.eframe.test.BaseSpecification
import org.simplemes.mes.demand.domain.Order
import spock.lang.Unroll

/*
 * Copyright Michael Houston. All rights reserved.
 *
*/

/**
 * Tests for OrderReleaseRequest.
 */
class OrderReleaseRequestSpec extends BaseSpecification {

  def "test convenience constructor with order"() {
    given: 'an order'
    def order = new Order(order: 'M001')

    when: 'a request is created with the convenience constructor'
    def request = new OrderReleaseRequest(order)

    then: 'the order is set'
    request.order == order

    and: 'other fields are null'
    request.qty == null
    request.dateTime == null
  }

  def "test map constructor"() {
    when: 'a request is created with map constructor'
    def request = new OrderReleaseRequest(order: new Order(order: 'M001'), qty: 10.5)

    then: 'the fields are set correctly'
    request.order != null
    request.qty == 10.5
  }

  def "test empty constructor"() {
    when: 'a request is created with empty constructor'
    def request = new OrderReleaseRequest()

    then: 'the fields are null'
    request.order == null
    request.qty == null
    request.dateTime == null
  }

  def "test map constructor with all fields"() {
    given: 'a date'
    def date = new Date()

    when: 'a request is created with all fields'
    def request = new OrderReleaseRequest(order: new Order(order: 'M001'), qty: 100.0, dateTime: date)

    then: 'all fields are set'
    request.order != null
    request.qty == 100.0
    request.dateTime == date
  }

  @Unroll
  def "test toString includes field names - #field"() {
    given: 'a request with values'
    def request = new OrderReleaseRequest(qty: 10.0)

    when: 'toString is called'
    def s = request.toString()

    then: 'the field name is in the string'
    s.contains(field)

    where:
    field << ['qty', 'dateTime']
  }

  def "test equalsAndHashCode"() {
    given: 'two requests with same values'
    def order = new Order(order: 'M001')
    def request1 = new OrderReleaseRequest(order: order, qty: 10.0)
    def request2 = new OrderReleaseRequest(order: order, qty: 10.0)

    expect: 'they are equal'
    request1 == request2
    request1.hashCode() == request2.hashCode()
  }

  def "test equalsAndHashCode differs when order differs"() {
    given: 'two requests with different orders'
    def request1 = new OrderReleaseRequest(order: new Order(order: 'M001'), qty: 10.0)
    def request2 = new OrderReleaseRequest(order: new Order(order: 'M002'), qty: 10.0)

    expect: 'they are not equal'
    request1 != request2
  }

  def "test equalsAndHashCode differs when qty differs"() {
    given: 'two requests with different qty'
    def order = new Order(order: 'M001')
    def request1 = new OrderReleaseRequest(order: order, qty: 10.0)
    def request2 = new OrderReleaseRequest(order: order, qty: 20.0)

    expect: 'they are not equal'
    request1 != request2
  }

}
