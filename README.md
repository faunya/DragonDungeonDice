# Dungeon Dragon Dice
Text based RPG Android app with a dice rolling mechanic. 

Collaborated with Yijing Zheng (yijingzheng) and Wanyue Wang (wanyuew).  
All commits under imstuff and lokis-mochi are by me. One is an old username that wasn't changed locally and the other is an alt account.

I designed the gameplay and coded the actual game portion of the app. It's pretty rough but I didn't have too much time to refine it.

## Gameplay  

The game is turn based, with whoever having the highest SPD stat go first. When you make an attack or use an ability, you roll to see whether you hit or not.
There are three actions available:

- Attack  
Basic attack. Does D4 + Str modifier dmg
- Abilities  
Different abilities that may use different dices for rolling dmg and different stat modifiers.
- Block  
Braces for the next attack from the enemy, reducing damage to a third.

### Stats
- Strength (Str)  
Str affects how much dmg you do with your basic attacks.
- Dexterity (Dex)  
Dex affects your chance to hit, lowering or raising enemy AC
- Vitality (Vit)  
Vit affects your max HP and how much you recover from resting
- Wisdom (Wis)  
Wis affects your max SP  
- Intelligence (Int)  
Int affects magic spell dmg  
- Speed (Spd)  
Spd affects how fast your turn comes up  

### Abilities
Abilities use SP or "Special Points." You can see how much SP you have left from the SP bar. SP is recovered by 1 point from basic attacking, whether it misses or hits. 

## To-Do List
- Indicator on how much SP each ability uses in the UI
- Proper death
- Turn indicator
