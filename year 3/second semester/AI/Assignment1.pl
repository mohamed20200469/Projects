friend(ahmed, samy).
friend(ahmed, fouad).
friend(samy, mohammed).
friend(samy, said).
friend(samy, omar).
friend(samy, abdullah).
friend(fouad, abdullah).
friend(abdullah, khaled).
friend(abdullah, ibrahim).
friend(abdullah, omar).
friend(mostafa, marwan).
friend(marwan, hassan).
friend(hassan, ali).

friend(hend, aisha).
friend(hend, mariam).
friend(hend, khadija).
friend(huda, mariam).
friend(huda, aisha).
friend(huda, lamia).
friend(mariam, hagar).
friend(mariam, zainab).
friend(aisha, zainab).
friend(lamia, zainab).
friend(zainab, rokaya).
friend(zainab, eman).
friend(eman, laila).

% Define a predicate to check if either X or Y are friends to each other
is_friend(X,Y):-
	friend(X,Y);
	friend(Y,X).

% Define a predicate to append two lists L1 and L2
append([], L, L).
append([H|T], L, [H|R]) :- append(T, L, R).

% Define a predicate to check if X is not a member of the list L
is_not_member(_, []) :- true.  % if the list is empty, X is not a member
is_not_member(X, [Y|T]) :-
    X \= Y,  % if X is not equal to the first element, check the rest of the list
    is_not_member(X, T).
	
% Define the base case: an empty list reversed is an empty list
reverse([], []).

% Define the recursive case: to reverse a non-empty list, reverse the tail and append the head to the end
reverse([H|T], R) :-
    reverse(T, RevT),  % reverse the tail
    append(RevT, [H], R).  % append the head to the end

% Define a helper predicate to recursively find friends
get_friends_helper(X, L, Friends) :-
    is_friend(X, Y),  % find a friend of X
    is_not_member(Y, L),  % check that Y is not already in the accumulator list
    append([Y], L, NewList),  % add Y to the accumulator list
    get_friends_helper(X, NewList, Friends);  % continue searching for friends
    reverse(L, Friends).  % reverse the accumulator list to get the final list of friends
	
% Define a predicate to get all friends of an atom
friendList(X, Friends) :-
    get_friends_helper(X, [], Friends), !.

% Define the base case: the count of an empty list is 0
count([], 0).

% Define the recursive case: the count of a non-empty list is 1 plus the count of the tail
count([_|T], Count) :-
    count(T, TailCount),  % count the tail of the list
    Count is TailCount + 1.  % add 1 to the count of the tail

% Define the predicate to get the count
friendListCount(Person, N):-
	friendList(Person, FriendsList),
	count(FriendsList, N).
	
% Define the rule to suggest friends
peopleYouMayKnow(Person, SuggestedFriend):-
	is_friend(Person, CommonFriend),
	is_friend(CommonFriend, SuggestedFriend),
	Person \= SuggestedFriend.
	
% Define the rule to find a mutual friend between two people
mutual(Person, Friend, Common):-
	is_friend(Person, Common),
	is_friend(Common, Friend),
	Person \= Friend.
	
% Define the predicate to find possible friend suggested
peopleYouMayKnowList(Person, SuggestedFriends):-
	possibleFriendsHelper(Person, [], SuggestedFriends), !.
	
% Define a helper predicate to recursively search for proper friends
possibleFriendsHelper(Person, List, SuggestedFriends):-
	peopleYouMayKnow(Person, Friend), % finding a suggested friend from mutuals
	is_not_member(Friend, List), % checking if he is not already in the list of suggested friends
	append([Friend], List, ResL), % adding the friend to the list of suggested friends
	possibleFriendsHelper(Person, ResL, SuggestedFriends); % recursive call with the new list
	reverse(List, SuggestedFriends). % reverse the accumulator list to get the final list of friends in the proper order

% bonus	
peopleYouMayKnow_indirect(Person,SuggestedFriend):-
    is_friend(Person,Friend), % finding a friend
    is_friend(Friend,FriendOfAFriend), % finding a friend of a friend
    Person\= FriendOfAFriend, % making sure the person is'nt the friend of a the friend
    is_friend(FriendOfAFriend,SuggestedFriend), % finding the friend to suggest
    Friend\=SuggestedFriend, % making sure the suggested friend isn't the original friend
    Person\= SuggestedFriend, % making sure the suggested friend isn't the person
	FriendOfAFriend\=SuggestedFriend, % making sure the suggested friend isn't the friend of the friend
    \+(is_friend(Person,SuggestedFriend)), % making sure the suggested friend is'nt already a friend of the person
   \+(peopleYouMayKnow(Person,SuggestedFriend)) . % making sure the suggested friend doesn't have any direct mutual friends with the person