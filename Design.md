# Deckbuilding API

## Beans

### Players / Admin
* username: String
* password: String
* cards: Map of Card, Amount
* decks: Set of Decks
* isAdmin: Boolean

### Cards
* name: String
* type: Enum
* archetype: Enum
* rarity: Integer
* special Effects: Set of Enums
* isBanned: Boolean
* isUnique: Boolean
#### Monster Cards
* attackValue: Integer
* defenseVaule: Integer
#### Spell Cards
* damageValue: Integer

Other stuff?
* ReleaseYear: Integer

### Decks
20 cards main archetype, 10 cards sub archetype
* primaryArchetype: Enum
* secondaryArchetype: Enum
* creator: Player
* cards: Map of Card, Amount


## History Logs
Keeping track of data
* Transactions
* Cards entering circulation
* Popular cards (checking the meta)

## Stories

Players have cards

Players can make decks

Players can see what decks they have cards for

Players can sort cards by characteristics
* Card type (monster, spell, trap)
* Archetype (Earth, Wind, Fire, Water)
* Rarity
* Special effects
* Banned cards
* Unique (Only one in deck)

Players can find ideal trading partners
* Looking for deck, finding people with cards you need
* I have cards you need, you have cards I need

Admins can
* Revert trades
* View Logs

## Database


1. How are we searching cards?
2. How are we storing cards that players own?
3. How are we storing cards in a deck?

### Searching Cards
	Archetype Card Type  as parition key
	Rarity as cluster key

### Storing Cards in a Player

### Storing Cards in a Deck






























