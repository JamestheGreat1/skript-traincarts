# skript-traincarts

A Skript addon that exposes the TrainCarts API to Skript.

Create custom TrainCarts trigger signs, launch trains, control ride behavior, and build fully scripted attractions without writing Java.

| Feature                  | Supported |
| ------------------------ | :-------: |
| Custom TrainCarts Events |     ✅     |
| Train Expressions        |     ✅     |
| Passenger Expressions    |     ✅     |
| Launch Effects           |     ✅     |
| Redstone Support         |     ✅     |
| Launch Units             |     ✅     |

---

## Features

- Custom TrainCarts sign trigger event
- Access the triggering train, cart, passengers, sign, and rail
- Launch trains
- Stop, reverse, and destroy trains
- Eject passengers
- Full TrainCarts redstone behavior
- Supports TrainCarts launch units (mph, kph, bpt, etc.)

---

## Requirements

- Paper 1.21+
- Skript 2.15+
- TrainCarts
- BKCommonLib

---

## Installation

1. Install:
   - Skript
   - TrainCarts
   - BKCommonLib

2. Place `skript-traincarts.jar` into your plugins folder.

3. Restart the server.

---

# Creating a Skript Trigger Sign

```
[train]
skript
dispatch
```

or

```
[cart]
skript
photo
```

The third line becomes the trigger.

---

# Events

```vb
on traincarts sign trigger:
```

```vb
on traincarts sign trigger "dispatch":
```

---

# Expressions

## Event

```vb
event-train
event-cart
event-passengers

event-trigger
event-data

event-action

event-sign
event-rail

event-sign-type
```

## Sign

```vb
line 1 of traincarts sign
line 2 of traincarts sign
line 3 of traincarts sign
line 4 of traincarts sign
```

---

# Effects

## Train Control

```vb
stop event-train

reverse event-train

destroy event-train
```

## Launch

```vb
launch event-train at 10 mph

launch event-train at 20 mph over 3 seconds

launch event-train at 20 mph over 50 blocks
```

Supported units:

- bpt
- bps
- m/s
- mph
- kph
- km/h

---

## Passenger Control

```vb
eject event-passengers
```

---

# Conditions

```vb
if event-train is empty:

if event-train has passengers:

if event-sign is powered:
```

---

# Examples

## Dispatch

```vb
on traincarts sign trigger "dispatch":
    wait 2 seconds
    launch event-train at 18 mph over 25 blocks
```

## Emergency Stop

```vb
on traincarts sign trigger "estop":
    stop event-train
```

## Photo

```vb
on traincarts sign trigger "photo":
    loop event-passengers:
        # Your photo script here
```

---

# Development

This project currently compiles against locally supplied API jars.

Place the following inside the project's `libs/` folder:

- BKCommonLib.jar
- TrainCarts.jar
- Skript.jar

---

# License

MIT License
