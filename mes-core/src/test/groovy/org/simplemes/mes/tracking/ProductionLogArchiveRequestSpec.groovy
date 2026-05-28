package org.simplemes.mes.tracking

import org.simplemes.eframe.test.BaseSpecification
import spock.lang.Unroll

/*
 * Copyright Michael Houston. All rights reserved.
 *
*/

/**
 * Tests for ProductionLogArchiveRequest.
 */
class ProductionLogArchiveRequestSpec extends BaseSpecification {

  def "test default values"() {
    when: 'a request is created with no values'
    def request = new ProductionLogArchiveRequest()

    then: 'the defaults are set correctly'
    request.batchSize == 500
    request.delete == false
    request.ageDays == null
  }

  def "test field assignments"() {
    given: 'a request with values'
    def request = new ProductionLogArchiveRequest(
      ageDays: 30.5,
      batchSize: 1000,
      delete: true
    )

    expect: 'the values are set correctly'
    request.ageDays == 30.5
    request.batchSize == 1000
    request.delete == true
  }

  @Unroll
  def "test toString includes field names - #field"() {
    given: 'a request with values'
    def request = new ProductionLogArchiveRequest(ageDays: 30.5, batchSize: 1000)

    when: 'toString is called'
    def s = request.toString()

    then: 'the field name is in the string'
    s.contains(field)

    where:
    field << ['ageDays', 'batchSize', 'delete']
  }

  def "test equalsAndHashCode uses specified fields"() {
    given: 'two requests with same critical fields'
    def request1 = new ProductionLogArchiveRequest(ageDays: 30.5, batchSize: 1000, delete: false)
    def request2 = new ProductionLogArchiveRequest(ageDays: 30.5, batchSize: 1000, delete: false)

    expect: 'they are equal'
    request1 == request2
    request1.hashCode() == request2.hashCode()
  }

  def "test equalsAndHashCode differs when ageDays differs"() {
    given: 'two requests with different ageDays'
    def request1 = new ProductionLogArchiveRequest(ageDays: 30.5, batchSize: 1000, delete: false)
    def request2 = new ProductionLogArchiveRequest(ageDays: 60.0, batchSize: 1000, delete: false)

    expect: 'they are not equal'
    request1 != request2
  }

  def "test equalsAndHashCode differs when batchSize differs"() {
    given: 'two requests with different batchSize'
    def request1 = new ProductionLogArchiveRequest(ageDays: 30.5, batchSize: 1000, delete: false)
    def request2 = new ProductionLogArchiveRequest(ageDays: 30.5, batchSize: 500, delete: false)

    expect: 'they are not equal'
    request1 != request2
  }

  def "test equalsAndHashCode differs when delete differs"() {
    given: 'two requests with different delete flag'
    def request1 = new ProductionLogArchiveRequest(ageDays: 30.5, batchSize: 1000, delete: false)
    def request2 = new ProductionLogArchiveRequest(ageDays: 30.5, batchSize: 1000, delete: true)

    expect: 'they are not equal'
    request1 != request2
  }

  def "test ageDays can be fractional"() {
    when: 'a request is created with fractional ageDays'
    def request = new ProductionLogArchiveRequest(ageDays: 0.5)

    then: 'the fractional value is preserved'
    request.ageDays == 0.5
  }

}
