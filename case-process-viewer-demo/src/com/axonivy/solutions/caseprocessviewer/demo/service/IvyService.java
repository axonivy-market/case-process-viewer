package com.axonivy.solutions.caseprocessviewer.demo.service;

import java.util.List;
import java.util.Optional;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.security.exec.Sudo;
import ch.ivyteam.ivy.workflow.ITask;
import ch.ivyteam.ivy.workflow.query.CaseQuery;
import ch.ivyteam.ivy.workflow.query.TaskQuery;

public class IvyService {
  private IvyService() {}

  public static List<ITask> findAllTasksOfCurrentCase() {
    return Sudo.get(() -> {
      String currentCaseUUID = Ivy.wfCase().uuid();
      var caseQuery = CaseQuery.businessCases().where().uuid().isEqual(currentCaseUUID);
      return TaskQuery.create().where().cases(caseQuery).executor().results();
    });
  }

  public static boolean hasUndoneTask(List<ITask> tasks) {
    return Optional.ofNullable(tasks).filter(list -> !list.isEmpty())
        .map(list -> list.stream().anyMatch(task -> task.getState().isActive() && task.getId() != Ivy.wfTask().getId()))
        .orElse(true);
  }
}
