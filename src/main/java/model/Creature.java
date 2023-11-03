package model;

public class Creature {
    private String name;
    private double initiative;
    private int HP;
    private int maxHP;
    private boolean concentration;
    private boolean blinded;
    private boolean charmed;
    private boolean deafened;
    private boolean frightened;
    private boolean grappled;
    private boolean incapacitated;
    private boolean invisible;
    private boolean paralyzed;
    private boolean petrified;
    private boolean poisoned;
    private boolean prone;
    private boolean restrained;
    private boolean stunned;
    private boolean unconscious;
    private boolean exhaustion;
    private String extraInfo;
    private int tempHP;
    private int exhaustionLevel;
    private int AC;
    private int originalMaxHP;
    private int legendaryResistances;
    private int legendaryResistancesLeft;
    private int legendaryActions;
    private int legendaryActionsLeft;

    public Creature(String name, double initiative, int HP, int maxHP, int legendaryResistances, int legendaryActions) {
        this(name, initiative, HP, maxHP);
        this.legendaryResistances = legendaryResistances;
        this.legendaryActions = legendaryActions;
        this.legendaryResistancesLeft = legendaryResistances;
        this.legendaryActionsLeft = legendaryActions;
    }

    public Creature(String name, double initiative, int HP, int maxHP) {
        this.name = name;
        this.initiative = initiative;
        this.HP = HP;
        this.maxHP = maxHP;
        this.originalMaxHP = maxHP;
        this.AC = 0;
        this.concentration = false;
        this.blinded = false;
        this.charmed = false;
        this.deafened = false;
        this.frightened = false;
        this.grappled = false;
        this.incapacitated = false;
        this.invisible = false;
        this.paralyzed = false;
        this.petrified = false;
        this.poisoned = false;
        this.prone = false;
        this.restrained = false;
        this.stunned = false;
        this.unconscious = false;
        this.exhaustion = false;
        this.extraInfo = "";
        this.tempHP = 0;
        this.exhaustionLevel = 0;
        this.legendaryResistances = 0;
        this.legendaryResistancesLeft = 0;
        this.legendaryActions = 0;
        this.legendaryActionsLeft = 0;
    }

    public String getName() {
        return name;
    }

    public double getInitiative() {
        return initiative;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public boolean isConcentration() {
        return concentration;
    }

    public void setConcentration(boolean concentration) {
        this.concentration = concentration;
    }

    public boolean isBlinded() {
        return blinded;
    }

    public void setBlinded(boolean blinded) {
        this.blinded = blinded;
    }

    public boolean isCharmed() {
        return charmed;
    }

    public void setCharmed(boolean charmed) {
        this.charmed = charmed;
    }

    public boolean isDeafened() {
        return deafened;
    }

    public void setDeafened(boolean deafened) {
        this.deafened = deafened;
    }

    public boolean isFrightened() {
        return frightened;
    }

    public void setFrightened(boolean frightened) {
        this.frightened = frightened;
    }

    public boolean isGrappled() {
        return grappled;
    }

    public void setGrappled(boolean grappled) {
        this.grappled = grappled;
    }

    public boolean isIncapacitated() {
        return incapacitated;
    }

    public void setIncapacitated(boolean incapacitated) {
        this.incapacitated = incapacitated;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public boolean isParalyzed() {
        return paralyzed;
    }

    public void setParalyzed(boolean paralyzed) {
        this.paralyzed = paralyzed;
    }

    public boolean isPetrified() {
        return petrified;
    }

    public void setPetrified(boolean petrified) {
        this.petrified = petrified;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }

    public boolean isProne() {
        return prone;
    }

    public void setProne(boolean prone) {
        this.prone = prone;
    }

    public boolean isRestrained() {
        return restrained;
    }

    public void setRestrained(boolean restrained) {
        this.restrained = restrained;
    }

    public boolean isStunned() {
        return stunned;
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    public boolean isUnconscious() {
        return unconscious;
    }

    public void setUnconscious(boolean unconscious) {
        this.unconscious = unconscious;
    }

    public boolean isExhaustion() {
        return exhaustion;
    }

    public void setExhaustion(boolean exhaustion) {
        this.exhaustion = exhaustion;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public int getTempHP() {
        return tempHP;
    }

    public void setTempHP(int tempHP) {
        this.tempHP = tempHP;
    }

    public int getExhaustionLevel() {
        return exhaustionLevel;
    }

    public void setExhaustionLevel(int exhaustionLevel) {
        this.exhaustionLevel = exhaustionLevel;
    }

    public int getAC() {
        return AC;
    }

    public void setAC(int AC) {
        this.AC = AC;
    }

    public int getOriginalMaxHP() {
        return originalMaxHP;
    }

    public int getLegendaryResistances() {
        return legendaryResistances;
    }

    public void setLegendaryResistances(int legendaryResistances) {
        this.legendaryResistances = legendaryResistances;
    }

    public int getLegendaryActions() {
        return legendaryActions;
    }

    public void setLegendaryActions(int legendaryActions) {
        this.legendaryActions = legendaryActions;
    }

    @Override
    public String toString() {
        return this.name + " (" + this.initiative + ")";
    }
}
