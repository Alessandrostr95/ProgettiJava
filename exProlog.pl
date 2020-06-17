%https://www.ic.unicamp.br/~meidanis/courses/mc336/2009s2/prolog/problemas/

%Ci sono le soluzioni ma cerchiamo di non guardarle e risolvere insieme


%=================LISTS===================

%1-Trova l'ultimo elemento di una lista

%ultimo(?Elem, +Lista)

ultimo(X,[X]).

ultimo(X,[_Head|Tail]):-
    ultimo(X,Tail).



%2-Trova il penultimo elemento di una lista

%penultimo(?PenElem, +Lista)

penultimo(X,[X,_Y]).

penultimo(X,[_Testa|Coda]):-
    penultimo(X,Coda).



%3-Trova il k esimo elemento di una lista
%Conto a partire da 0

%elem_k(?ElemK, +Lista, ?PosizK)

elem_k(X,[X|_Coda],0).

elem_k(X,[_Testa|Coda],N):-
    elem_k(X,Coda,N1),
    N is N1+1.



%4-Trova il numero di elementi in una lista

%num_elem_lista(?NumElementi, +Lista)

num_elem_lista(0,[]).

num_elem_lista(N,[_Testa|Coda]):-
    num_elem_lista(N1,Coda),
    N is N1+1.



%5-Inverti una lista

%inverti_lista(?ListaInvertita, +ListaDaInvertire)

inverti_lista([],[]).

inverti_lista(X,[Testa|Coda]):-
    inverti_lista(Y,Coda),
    append(Y,[Testa],X).



%6-Verifica se una lista e' palindroma

%palindroma(+Lista)

palindroma([]).
palindroma([_X]).

palindroma([Testa|Coda]):-
    ultimo(Testa,Coda),
    inverti_lista(CodaInvertita,Coda),
    CodaInvertita =[_PrimoEl|Resto],
    palindroma(Resto).
 


%7-"Normalizza" una struttura a liste annidate

%my_flatten(?ListaFlat, +ListaAnnidata)

my_flatten([],[]).

my_flatten(FlatList, Nested):-
    Nested = [H|T],
    var(H),
    my_flatten(FlatList2, T),
    append([H], FlatList2, FlatList).

my_flatten(FlatList, Nested):-
    Nested = [H|T],
    is_list(H),
    my_flatten(FlatTemp,H),
    my_flatten(FlatList2,T),
    append(FlatTemp,FlatList2,FlatList),!.

my_flatten(FlatList, Nested):-
    Nested = [H|T],
    nonvar(H),
    my_flatten(FlatList2, T),
    append([H], FlatList2, FlatList).
 


%8-Elimina gli elementi ripetuti consecutivi in una lista

%compress(?ListaCompressa, +ListaConRipetizioni)

compress([X],[X]).

compress(ComprList,[X,Y|Tail]):-
    dif(X,Y),
    %X \= Y,
    compress(Temp, [Y|Tail]),
    append([X], Temp, ComprList).

compress(ComprList,[X,Y|Tail]):-
    X = Y,
    compress(ComprList, [Y|Tail]).



%9-Raggruppa elementi consecutivi di una lista in una sottolista

%pack(+InitialList, ?PackedList)
/*
pack([X], [X]).

pack([E1,E2|Tail], Packed):-
    E1 = E2,
    packTemp([E2|Tail], TPl, [E1]),
    Packed = [[E1|TPl]].

pack([E1,E2|Tail], Pl):-
    dif(E1,E2),
    pack([E2|Tail], TPl),
    append([E2], TPl, Pl).


packTemp([X], [Temp|X], Temp).

packTemp([E1,E2|Tail], TPl, Temp):-
    E1 = E2,
    append(Temp,[E2],TPl),
    packTemp([E2|Tail], TT, TPl).

packTemp([E1,E2|Tail], TPl, Temp):-
    dif(E1,E2),
    append(Temp, [E1], TPl).
*/

%10-Implementa run-lenght encoding su una lista
%Leggere consegna

%encode(+List, ?EncList)

%11-Implementa modified run-length encoding su una lista 

%12-Decodifica un lista run-length encoded

%13-

%=======ARITHMERIC===================

%31-Determina se un numero e' primo
%is_prime(7)
%????


%32-Determina il massimo comun divisore di due interi



%=======LOGIC and CODES===============





%========BINARY TREES=====================




%=======MULTIWAY TREES=======================




%===========GRAPHS===========================




%============MISCELLANEOUS PROBLEMS==============
