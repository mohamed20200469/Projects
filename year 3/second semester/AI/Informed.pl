%Sample input ---> set_rows(3).
%Sample input ---> set_columns(3).
%Sample input ---> informedSearch([[[e,e,b,b,e,e,e,e,e], null]],  [[[e,e,b,b,e,e,e,e,e], null, 0, x, 0]],[]).
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

informedSearch(Open, NewOpen, Closed):-
uninformedSearch(Open, Closed, Goal),
countDominos(Goal, CurrentNumberOfDominos),
checkIfMax(CurrentNumberOfDominos),
search(NewOpen, Closed, Goal), !.

search(Open, Closed, Goal):-
getBestState(Open, [CurrentState,Parent,G,H,F], _), % Step 1
CurrentState = Goal,
countDominos(CurrentState, CurrentNumberOfDominos),
write("Max number of dominos is "), write(CurrentNumberOfDominos), nl, !.


search(Open, Closed, Goal):-
getBestState(Open, CurrentNode, TmpOpen),
getAllValidChildren1(CurrentNode,TmpOpen,Closed,Goal,Children), % Step3
addChildren(Children, TmpOpen, NewOpen), % Step 4
append(Closed, [CurrentNode], NewClosed), % Step 5.1
search(NewOpen, NewClosed, Goal). % Step 5.2
	
% Implementation of step 3 to get the next states
getAllValidChildren1(Node, Open, Closed, Goal, Children):-
findall(Next, getNextState1(Node,Open,Closed,Goal,Next),
Children).


getNextState1([State,_,G,_,_],Open,Closed,Goal,[Next,State,NewG,NewH,NewF]):-
move(State, Next),
isOkay(Next),
calculateH(Next, Goal, NewH),
NewG is G + 1,
NewF is NewG + NewH,
( not(member([Next,_,_,_,_], Open)) ; memberButBetter(Next,Open,NewF) ),
( not(member([Next,_,_,_,_],Closed));memberButBetter(Next,Closed,NewF)).

memberButBetter(Next, List, NewF):-
findall(F, member([Next,_,_,_,F], List), Numbers),
min_list(Numbers, MinOldF),
MinOldF > NewF.

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

getBestState(Open, BestChild, Rest):-
findMin(Open, BestChild),
delete(Open, BestChild, Rest).


% Implementation of findMin in getBestState determines the search alg.
% A* search
findMin([X], X):- !.

findMin([Head|T], Min):-
findMin(T, TmpMin),
Head = [_,_,_,HeadH,HeadF],
TmpMin = [_,_,_,TmpH,TmpF],
(TmpF < HeadF -> Min = TmpMin ; Min = Head).


%Heuristic function calculated by number of incorrect elements in matrix
calculateH([], [], 0):- !.
calculateH([Head|T1], [Head|T2], Hvalue):-
!, calculateH(T1, T2, Hvalue).
calculateH([_|T1], [_|T2], Hvalue):-
calculateH(T1, T2, Count),
Hvalue is Count + 1.


%Predicate checks if the number of domino pieces is the maximum number that the board can take
checkIfMax(CurrentNumberOfDominos):-
columns(C),
rows(R),
Max is ((R * C) - 2) // 2,
CurrentNumberOfDominos = Max, !.

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