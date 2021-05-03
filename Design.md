# Deckbuilding API

The deckbuilding API is a REST web application that allows users 

## Beans

### Users / Admin
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

### Friday 
2	Users can collect cards
2	Users can view their cards
2	Users can view all cards

2	Users can make decks
2	Users can view their decks
2	Users can compare decks to what cards they have

5	Users can sort their cards by characteristics
* Card type (monster, spell, trap)
* Archetype (Earth, Wind, Fire, Water)
* Rarity
* Special effects
* Banned cards
* Unique (Only one in deck)

5	Users can find ideal trading partners
* Looking for deck, finding people with cards you need
* I have cards you need, you have cards I need

5	Admins can revert trades
3	Admins can view logs

### Monday
1-As a user, I can register an account.
2-As a user, I can login to an account.
3-As a user, I can log out from an account.
4-As a user, I can create a new deck.
5-As a user, I can view cards in my decks.
6-As a user, I can delete a deck.
7-As a user, I can collect cards (how?)
8-As a user, I can view any missing cards that are in the system I don't have
9-As a user, I can view all the cards available in the system.
10-As a user, I can view all the cards I own
11-As a user, I can view all users in the system who have cards.
12-As a user, I can see the cards that are meta to the card game.
13-As a user, I can add and remove cards from a deck.
14-As a user, I can view other users’ decks.
15-As a user, I can interact with a user for a trade request.
16-As a user, I can accept the interaction with a user who started the trade request.
17-As a user, I can accept or deny the trade form by another user.
18-As a user, I can view my own trade histories and transaction history, popular cards.
19-As an Admin, I can remove (ban) a user from the system.
20-As an Admin, I can add or remove a card in the database.
21-As an Admin, I can change the functionality of the card’s stats, such as archetype, type, if it is unique, if it is banned.
22-As an Admin, I can view all users' trade histories and transaction history, popular cards.

------if we have more time? ------
23-As an Admin, I can change role of user to from a user to a mod, vice versa.
24-As a Mod, I can remove (ban) a user from the system.

## Database

1. How are we searching cards?
2. How are we storing cards that users own?
3. How are we storing cards in a deck?

### Searching Cards
	(Archetype, Card Type)  as parition key
	(Rarity, ???) as cluster key

### Storing Cards in a Player
	Primary key?
	json blob?

### Storing Cards in a Deck
	Primary key?
	json blob?






























