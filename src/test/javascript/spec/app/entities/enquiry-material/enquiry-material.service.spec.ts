import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EnquiryMaterialService } from 'app/entities/enquiry-material/enquiry-material.service';
import { IEnquiryMaterial, EnquiryMaterial } from 'app/shared/model/enquiry-material.model';

describe('Service Tests', () => {
  describe('EnquiryMaterial Service', () => {
    let injector: TestBed;
    let service: EnquiryMaterialService;
    let httpMock: HttpTestingController;
    let elemDefault: IEnquiryMaterial;
    let expectedResult: IEnquiryMaterial | IEnquiryMaterial[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EnquiryMaterialService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EnquiryMaterial(0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EnquiryMaterial', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EnquiryMaterial()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EnquiryMaterial', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            dimension: 'BBBBBB',
            materialId: 1,
            color: 'BBBBBB',
            comments: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EnquiryMaterial', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            dimension: 'BBBBBB',
            materialId: 1,
            color: 'BBBBBB',
            comments: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a EnquiryMaterial', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
