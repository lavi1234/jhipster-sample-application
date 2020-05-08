import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStaticPages } from 'app/shared/model/static-pages.model';

@Component({
  selector: 'jhi-static-pages-detail',
  templateUrl: './static-pages-detail.component.html'
})
export class StaticPagesDetailComponent implements OnInit {
  staticPages: IStaticPages | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ staticPages }) => (this.staticPages = staticPages));
  }

  previousState(): void {
    window.history.back();
  }
}
