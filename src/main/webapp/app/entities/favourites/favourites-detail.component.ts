import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFavourites } from 'app/shared/model/favourites.model';

@Component({
  selector: 'jhi-favourites-detail',
  templateUrl: './favourites-detail.component.html'
})
export class FavouritesDetailComponent implements OnInit {
  favourites: IFavourites | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ favourites }) => (this.favourites = favourites));
  }

  previousState(): void {
    window.history.back();
  }
}
