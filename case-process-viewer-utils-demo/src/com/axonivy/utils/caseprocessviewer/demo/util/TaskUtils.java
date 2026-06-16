package com.axonivy.utils.caseprocessviewer.demo.util;

import java.util.List;

import com.axonivy.utils.caseprocessviewer.demo.service.IvyService;

import ch.ivyteam.ivy.workflow.ITask;

public class TaskUtils {

  public static boolean canProcessToNextTask() {
    List<ITask> tasks = IvyService.findAllTasksOfCurrentCase();
    return !IvyService.hasUndoneTask(tasks);
  }

}
