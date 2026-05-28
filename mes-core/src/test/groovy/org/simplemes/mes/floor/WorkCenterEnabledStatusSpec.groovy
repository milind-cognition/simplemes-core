package org.simplemes.mes.floor

import org.simplemes.eframe.test.BaseSpecification

/*
 * Copyright Michael Houston. All rights reserved.
 *
*/

/**
 * Tests for WorkCenterEnabledStatus.
 */
class WorkCenterEnabledStatusSpec extends BaseSpecification {

  def "test instance is singleton-like"() {
    expect: 'the instance is not null'
    WorkCenterEnabledStatus.instance != null

    and: 'multiple calls return the same instance'
    WorkCenterEnabledStatus.instance.is(WorkCenterEnabledStatus.instance)
  }

  def "test isEnabled returns true"() {
    expect: 'the status is enabled'
    WorkCenterEnabledStatus.instance.isEnabled()
  }

  def "test getId returns correct ID"() {
    expect: 'the ID is correct'
    WorkCenterEnabledStatus.instance.getId() == 'ENABLED'
  }

  def "test defaultChoice is true"() {
    expect: 'defaultChoice is set to true in constructor'
    WorkCenterEnabledStatus.instance.defaultChoice == true
  }

  def "test constructor creates instance"() {
    when: 'a new instance is created'
    def status = new WorkCenterEnabledStatus()

    then: 'the instance is enabled'
    status.isEnabled()

    and: 'the ID is correct'
    status.getId() == 'ENABLED'

    and: 'defaultChoice is set'
    status.defaultChoice == true
  }

}
