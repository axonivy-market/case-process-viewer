# Case Process Viewer

This Axon Ivy component visually represents the process flow of your current case. It highlights both the current active task and all completed tasks directly on the process diagram, helping users quickly understand the case’s progress at a glance. Designed for smooth UI integration, it can be easily embedded into any UI.

Key Features:

- Dynamically draws the process diagram of the current case

- Highlights the current task and completed tasks

- Display execution frequency for process arrows

- Customizable colors via variables

- Provides an intuitive overview of case progress

- Simple to configure and integrate into existing Ivy user interfaces

![purchase-request-approval](images/purchase-request-approval.png)

## Demo

1. Start **Purchase Request Demo** process
2. Start **Purchase Request** task to view the status of the current task.
![purchase-request](images/purchase-request.png)


## Setup

**Add the Component to Your JSF Page**

   Include the component in your XHTML file using the following syntax:

   ```
   <ic:com.axonivy.solutions.caseprocessviewer.component.ProcessViewer header="Case Process Viewer" />
   ```

**Component Attributes**

| Attribute | Type | Required | Description |
|-----------|------|----------|-------------|
| `header` | `String` | No | Text label for the header (default: "Case Process Viewer") |

**Color configuration**

The Case Process Viewer supports customizable colors through variables. This allows you to adapt the visual appearance to your application’s theme without changing code.

| Color key | Description |
|-----------|------|
| `passedColor` | `Color used for arrows that have been run through` |
| `activeColor` | `Color used to highlight the currently active process element` |
| `frequencyColor` | `Background color used for frequency label` |
| `frequencyTextColor` | `Text color used for frequency values` |

```
@variables.yaml@
```