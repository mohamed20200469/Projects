%Sample input ---> set_rows(3).
%Sample input ---> set_columns(3).
%Sample input ---> uninformedSearch([[[e,e,b,b,e,e,e,e,e], null]],[], List).
%Close the program if you want to change the dimensions!!!!


% Declare dynamic predicate
:- dynamic rows/1.
:- dynamic columns/1.

% Set the value of the global variable
set_rows(Value) :-
    retractall(rows()),
    assertz(rows(Value)).

% Get the value of the global variable
get_rows(Value) :-
    rows(Value).
	
% Set the value of the global variable
set_columns(Value) :-
    retractall(columns()),
    assertz(columns(Value)).

% Get the value of the global variable
get_columns(Value) :-
    columns(Value).

% Predicate that takes a list and an index and replaces the old value with the new value
replace(Index, Old, New, List, Result) :-
    nth0(Index, List, Old, Rest),
    nth0(Index, Result, New, Rest).
	
% Predicate that checks for possible horizontal moves
checkForHorizontal(State):-
nth0(EmptyTileIndex, State, e),
columns(C),
CMinus1 is C - 1,
not(CMinus1 is EmptyTileIndex mod C), %checks that the empty index has an element to it's right
RightIndex is EmptyTileIndex + 1,
nth0(EmptyTileIndex, State, e),
nth0(RightIndex, State, e), !.

%Predicate that checks for possible vertical moves
checkForVertical(State) :-
nth0(EmptyTileIndex, State, e),
columns(C),
rows(R),
Number is EmptyTileIndex // C,
Number < R - 1, %checks that we the index is not in the last row
UnderIndex is EmptyTileIndex + C,
nth0(UnderIndex, State, e), !.

%Predicate that makes sure there are no possible horizontal or vertical moves in matrix
check(State):-
not(checkForHorizontal(State)),
not(checkForVertical(State)).


%Predicate that counts the occurrence of an element in a list
count(_, [], 0).
count(X, [X|T], N) :- count(X, T, N1), N is N1 + 1.
count(X, [Y|T], N) :- X \= Y, count(X, T, N).

%Predicate that uses the last predicate to count domino pieces on in matrix
countDominos(List, Number):-
count(d, List, N),
Number is N // 2.

%Uninformed main predicate, can be excuted as follows uninformedSearch([[[e,e,b,b,e,e,e,e,e], null]], [] , List)
%To output all possible matrix combinations
uninformedSearch(Open, Closed, Result):-
getState(Open, [CurrentState,Parent], _),
check(CurrentState), 
Result = CurrentState.
	
uninformedSearch(Open, Closed, Result):-
    getState(Open, CurrentNode, TmpOpen),
    getAllValidChildren(CurrentNode,TmpOpen,Closed,Children), % Step3
    addChildren(Children, TmpOpen, NewOpen), % Step 4
    append(Closed, [CurrentNode], NewClosed), % Step 5.1
    uninformedSearch(NewOpen, NewClosed, Result). % Step 5.2

% Implementation of step 3 to get the next states
getAllValidChildren(Node, Open, Closed, Children):-
findall(Next, getNextState(Node, Open, Closed, Next), Children).

getNextState([State, _], Open, Closed, [Next,State]):-
move(State, Next),
not(member([Next, _], Open)),
not(member([Next, _], Closed)),
isOkay(Next).

getState([CurrentNode|Rest], CurrentNode, Rest).

addChildren(Children, Open, NewOpen):-
append(Open, Children, NewOpen).
% Implementation of printSolution to print the actual solution path

move(State, Next):-
    horizontal(State, Next);
	vertical(State, Next).
	
horizontal(State, Next):-
nth0(EmptyTileIndex, State, e),
columns(C),
CMinus1 is C - 1,
not(CMinus1 is EmptyTileIndex mod C), %checks that the empty index has an element to it's right
RightIndex is EmptyTileIndex + 1,
nth0(RightIndex, State, e),
replace(EmptyTileIndex, e, d, State, NewState),
replace(RightIndex, e, d, NewState, Next).

vertical(State, Next):-
nth0(EmptyTileIndex, State, e),
columns(C),
rows(R),
Number is EmptyTileIndex / C,
Number < R - 1, %checks that we the index is not in the last row
UnderIndex is EmptyTileIndex + C,
nth0(UnderIndex, State, e),
replace(EmptyTileIndex, e, d, State, NewState),
replace(UnderIndex, e, d, NewState, Next).



isOkay(_):- true.