# Case Process Viewer

This Axon Ivy component visually represents the process flow of your current case. It highlights both the current active task and all completed tasks directly on the process diagram, helping users quickly understand the case’s progress at a glance. Designed for smooth UI integration, it can be easily embedded into any UI.

Key Features:

- Dynamically draws the process diagram of the current case

- Highlights the current task and completed tasks

- Display execution frequency for process arrows

- Customizable colors via variables

- Provides an intuitive overview of case progress

- Simple to configure and integrate into existing Ivy user interfaces

![purchase-request-with-label](images/purchase-request-with-label.png)

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
<br>

**Component Attributes**

| Attribute | Type | Required | Description |
|-----------|------|----------|-------------|
| `header` | `String` | No | Text label for the header (default: "Case Process Viewer") |

<br>

**Color configuration**

The Case Process Viewer supports customizable colors through variables. This allows you to adapt the visual appearance to your application’s theme without changing code.

**Accepted color syntax**

The component accepts the following color formats for the variables above:

- 3-digit hex: `#RGB` (e.g. `#0F8`)
- 4-digit hex (with alpha): `#RGBA` (e.g. `#0F8C`)
- 6-digit hex: `#RRGGBB` (e.g. `#00FF88`)
- 8-digit hex (with alpha): `#RRGGBBAA` (e.g. `#00FF88CC`)
- `rgb()` function: `rgb(r, g, b)` where `r`, `g`, `b` are integers 0–255 (e.g. `rgb(0, 255, 136)`).
- `rgba()` function: `rgba(r, g, b, a)` where `r`, `g`, `b` are integers 0–255 and `a` is a number between 0 and 1 (e.g. `rgba(0, 255, 136, 0.5)`).
- Named color strings: common CSS color names like `red`, `green`, `blue`, `transparent` (e.g. `red`).

Use any of these formats in your `variables.yaml` to configure the colors.

| Color key | Description | Default value |
|-----------|-------------| ------------- |
| `passedColor` | Color used for arrows that have been run through | `#47C46B` |
| `activeColor` | Color used to highlight the currently active process element | `#47C46B` |
| `frequencyColor` | Background color used for frequency label | `#47C46B` |
| `frequencyTextColor` | Text color used for frequency values | `#000000` |

<br>

```
@variables.yaml@
```