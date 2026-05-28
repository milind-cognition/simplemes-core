package org.simplemes.mes.floor

import org.simplemes.eframe.test.BaseSpecification

/*
 * Copyright Michael Houston. All rights reserved.
 *
*/

/**
 * Tests for WorkCenterDisabledStatus.
 */
class WorkCenterDisabledStatusSpec extends BaseSpecification {

  def "test instance is singleton-like"() {
    expect: 'the instance is not null'
    WorkCenterDisabledStatus.instance != null

    and: 'multiple calls return the same instance'
    WorkCenterDisabledStatus.instance.is(WorkCenterDisabledStatus.instance)
  }

  def "test isEnabled returns false"() {
    expect: 'the status is not enabled'
    !WorkCenterDisabledStatus.instance.isEnabled()
  }

  def "test getId returns correct ID"() {
    expect: 'the ID is correct'
    WorkCenterDisabledStatus.instance.getId() == 'DISABLED'
  }

  def "test constructor creates instance"() {
    when: 'a new instance is created'
    def status = new WorkCenterDisabledStatus()

    then: 'the instance is not enabled'
    !status.isEnabled()

    and: 'the ID is correct'
    status.getId() == 'DISABLED'
  }

}
