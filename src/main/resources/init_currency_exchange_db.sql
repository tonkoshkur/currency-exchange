create table if not exists Currencies
(
    ID       integer primary key autoincrement,
    Code     text(10) unique,
    FullName text(50),
    Sign     text(5)
);

create table if not exists ExchangeRates
(
    ID               integer primary key autoincrement,
    BaseCurrencyId   integer references Currencies (ID) on delete cascade,
    TargetCurrencyId integer references Currencies (ID) on delete cascade,
    Rate             Decimal(6),
    unique (BaseCurrencyId, TargetCurrencyId)
);

insert into Currencies (Code, FullName, Sign)
values ('USD', 'US Dollar', '$');
insert into Currencies (Code, FullName, Sign)
values ('EUR', 'Euro', '€');
insert into Currencies (Code, FullName, Sign)
values ('UAH', 'Ukrainian Hryvnia', '₴');
insert into Currencies (Code, FullName, Sign)
values ('RUB', 'Russian Ruble', '₽');

insert into ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate)
values (1, 2, 0.92);
insert into ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate)
values (1, 3, 38);
insert into ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate)
values (1, 4, 91.4);